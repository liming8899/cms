package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.*;
import cloud.onion.cms.model.convertor.ArticleConvertor;
import cloud.onion.cms.model.convertor.ResourceConvertor;
import cloud.onion.cms.model.entity.*;
import cloud.onion.cms.model.res.ArticleRes;
import cloud.onion.cms.model.res.FileRes;
import cloud.onion.cms.service.INavMenuService;
import cloud.onion.cms.service.IXhArticleService;
import cloud.onion.core.entity.Menu;
import cloud.onion.core.utils.HtmlDocUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author 允泽
 * @date 2022/8/1
 */
@Slf4j
@Service
public class XhArticleServiceImpl extends ServiceImpl<XhInfoContentMapper, XhInfoContent> implements IXhArticleService {

    @Autowired
    private XhInfoResourceMapper resourceMapper;

    @Autowired
    private XhContentEnMapper xhContentEnMapper;

    @Autowired
    private XhContentRuMapper xhContentRuMapper;

    @Autowired
    private XhContentReviewMapper xhContentReviewMapper;
    @Autowired
    private INavMenuService menuService;

    @Value("${xh.img.server}")
    private String xhImgServer;
    @Value("${xh.img.replace}")
    private String xhImageReplace;

    @Override
    public IPage<ArticleRes> getPageList(int currentPage, int pageSize, Long categoryId) {
        Menu menu = menuService.getById(categoryId);
        String[] menus = {};
        if (StringUtils.isNotBlank(menu.getXhCategoryId())) {
            menus = menu.getXhCategoryId().split(",");
        }
        Page<ArticleRes> page = new Page<>(currentPage, pageSize);
        IPage<ArticleRes> resIPage = super.getBaseMapper().getPageList(page, menus);

        List<ArticleRes> collect = resIPage.getRecords().stream().peek(e -> {
            String content = HtmlDocUtil.replaceImgSrc(e.getContent(), xhImgServer, xhImageReplace);
            e.setContent(content);

            String enContent = HtmlDocUtil.replaceImgSrc(e.getEnContent(), xhImgServer, xhImageReplace);
            e.setEnContent(enContent);

            String ruContent = HtmlDocUtil.replaceImgSrc(e.getRuContent(), xhImgServer, xhImageReplace);
            e.setRuContent(ruContent);

            if (StringUtils.isNotEmpty(e.getCoverImage())) {
                e.setCoverImage(e.getCoverImage().replace(xhImageReplace,xhImgServer));
            }
//            String coverImage = HtmlDocUtil.replaceImgSrc(e.getCoverImage(), xhImgServer, xhImageReplace);
//            if(coverImage !=null && coverImage.contains("<body>")) {
//                coverImage = coverImage.replaceAll("<body>","");
//            }
//            if(coverImage !=null && coverImage.contains("</body>")) {
//                coverImage = coverImage.replaceAll("</body>","");
//            }
        }).collect(Collectors.toList());
        resIPage.setRecords(collect);
        return resIPage;
    }

    @Override
    public ArticleRes getByPrimaryId(Long id) throws NotFoundException {
        // 查询详情
        LambdaQueryWrapper<XhInfoContent> entityQuery = Wrappers.<XhInfoContent>lambdaQuery();
        entityQuery.eq(XhInfoContent::getId, id);
        entityQuery.eq(XhInfoContent::getEisDel, 0);
        XhInfoContent entity = super.getOne(entityQuery);
        Optional.ofNullable(entity).orElseThrow(() -> new NotFoundException("shpt_info_content表中未查询到id为："+id+"的新闻"));

        ArticleRes articleRes = ArticleConvertor.INSTANCE.fromContent(entity);

        LambdaQueryWrapper<XhInfoContentReview> reviewLambdaQuery = Wrappers.<XhInfoContentReview>lambdaQuery();
        reviewLambdaQuery.eq(XhInfoContentReview::getContentId, id);
        reviewLambdaQuery.orderByDesc(XhInfoContentReview::getCreateTime);
        reviewLambdaQuery.last("limit 1");
        XhInfoContentReview xhInfoContentReview = xhContentReviewMapper.selectOne(reviewLambdaQuery);
        if (xhInfoContentReview != null && Objects.equals(xhInfoContentReview.getReviewStatus(), 1)) {
            LambdaQueryWrapper<XhInfoContentEn> enEntityQuery = Wrappers.<XhInfoContentEn>lambdaQuery();
            enEntityQuery.eq(XhInfoContentEn::getId, id);
            enEntityQuery.eq(XhInfoContentEn::getEisDel, 0);
            XhInfoContentEn entityEn = xhContentEnMapper.selectOne(enEntityQuery);

            LambdaQueryWrapper<XhInfoContentRu> ruEntityQuery = Wrappers.<XhInfoContentRu>lambdaQuery();
            ruEntityQuery.eq(XhInfoContentRu::getId, id);
            ruEntityQuery.eq(XhInfoContentRu::getEisDel, 0);
            XhInfoContentRu entityRu = xhContentRuMapper.selectOne(ruEntityQuery);

            articleRes.setEnTitle(entityEn == null ? "":entityEn.getTitle());
            articleRes.setRuTitle(entityRu == null ? "":entityRu.getTitle());
            articleRes.setEnDescription(entityEn == null ? "":entityEn.getSummary());
            articleRes.setRuDescription(entityRu == null ? "":entityRu.getSummary());
            articleRes.setEnSource(entityEn == null ? "":entityEn.getExternalSource());
            articleRes.setRuSource(entityRu == null ? "":entityRu.getExternalSource());
            articleRes.setEnContent(entityEn == null ? "":entityEn.getContent());
            articleRes.setRuContent(entityRu == null ? "":entityRu.getContent());
        }

        // 替换文本img
        String content = HtmlDocUtil.replaceImgSrc(articleRes.getContent(), xhImgServer, xhImageReplace);
        articleRes.setContent(content);
        String coverImage = HtmlDocUtil.replaceImgSrc(articleRes.getCoverImage(), xhImgServer, xhImageReplace);
        articleRes.setCoverImage(coverImage);
        // 获取附件
        LambdaQueryWrapper<XhInfoResource> filesQuery = Wrappers.<XhInfoResource>lambdaQuery();
        filesQuery.eq(XhInfoResource::getResType, "file");
        filesQuery.eq(XhInfoResource::getContentID, entity.getContentId());
        filesQuery.eq(XhInfoResource::getEisDel, 0);
        List<XhInfoResource> resources = resourceMapper.selectList(filesQuery);
        List<FileRes> files = resources.stream().parallel().map(ResourceConvertor.INSTANCE::fromResource).collect(Collectors.toList());
        // 设置附件
        List<FileRes> collect = files.stream().parallel().peek(e -> e.setUrl(e.getUrl().replace(xhImageReplace, xhImgServer))).collect(Collectors.toList());
        articleRes.setFiles(collect);

        // 更新阅读量
        CompletableFuture.runAsync(() -> {
            entity.setHits(entity.getHits()+1);
            super.updateById(entity);
        });
        return articleRes;
    }

    @Override
    public String getImageByCatalogId(Long id, String titleContent) {
        LambdaQueryWrapper<XhInfoContent> lambdaQuery = Wrappers.<XhInfoContent>lambdaQuery();
        lambdaQuery.eq(XhInfoContent::getEisDel,0);
        lambdaQuery.eq(XhInfoContent::getCatalogID, id);
        lambdaQuery.like(XhInfoContent::getTitle, titleContent);
        lambdaQuery.orderByDesc(XhInfoContent::getModifyTime);
        List<XhInfoContent> list = super.list(lambdaQuery);
        if (list.size() > 0) {
            XhInfoContent xhInfoContent = list.get(0);
            LambdaQueryWrapper<XhInfoResource> wrapper = Wrappers.<XhInfoResource>lambdaQuery();
            wrapper.eq(XhInfoResource::getEisDel,0);
            wrapper.eq(XhInfoResource::getContentID,xhInfoContent.getContentId());
            List<XhInfoResource> resources = resourceMapper.selectList(wrapper);
            if (resources.size() > 0) {
                XhInfoResource resource = resources.get(0);
                String attachUrl = resource.getAttachUrl();
                if (StringUtils.isNotBlank(attachUrl)) {
                    attachUrl = attachUrl.replace(xhImageReplace, "");
                    attachUrl = xhImgServer + "/" + attachUrl;
                }

                // 更新阅读量
                CompletableFuture.runAsync(() -> {
                    xhInfoContent.setHits(xhInfoContent.getHits()+1);
                    super.updateById(xhInfoContent);
                });
                return attachUrl;
            }
        }
        return "";
    }
}
