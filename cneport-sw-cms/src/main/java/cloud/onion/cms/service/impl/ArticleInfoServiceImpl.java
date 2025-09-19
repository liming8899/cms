package cloud.onion.cms.service.impl;


import cloud.onion.cms.mapper.ArticleInfoMapper;
import cloud.onion.cms.model.convertor.ArticleConvertor;
import cloud.onion.cms.model.entity.ArticleInfo;
import cloud.onion.cms.model.res.ArticleRes;
import cloud.onion.cms.model.res.CountryGuideRes;
import cloud.onion.cms.model.res.FileRes;
import cloud.onion.cms.service.IArticleInfoService;
import cloud.onion.core.constant.ReviewConst;
import cloud.onion.core.constant.StatusConst;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author 允泽
 * @date 2022/7/16
 */
@Service
public class ArticleInfoServiceImpl extends ServiceImpl<ArticleInfoMapper, ArticleInfo> implements IArticleInfoService {

    @Override
    public IPage<ArticleRes> pages(int currentPage, int pageSize, long categoryId, int isHot, String keywords, String otherSetting, Locale finalSessionLocale) {
        Page<ArticleInfo> pager = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<ArticleInfo> lambdaQuery = Wrappers.<ArticleInfo>lambdaQuery();
        lambdaQuery.eq(ArticleInfo::getStatus, StatusConst.NORMAL);
        lambdaQuery.eq(ArticleInfo::getIsReview, ReviewConst.YES);
        if (categoryId > 0) {
            lambdaQuery.eq(ArticleInfo::getCategoryId, categoryId);
        }
        if (isHot > 0) {
            lambdaQuery.eq(ArticleInfo::getIsHotSearch, isHot);
            lambdaQuery.orderByDesc(ArticleInfo::getIsHotSearch);
        }
        if (StringUtils.isNotBlank(keywords)) {
            lambdaQuery.like(ArticleInfo::getTitle,keywords);
        }

        if (StringUtils.isNotBlank(otherSetting)) {
            Map param = JSONObject.parseObject(otherSetting, Map.class);
            param.forEach((key, value) -> {
                if (value != null && StringUtils.isNotBlank(value.toString())) {
                    lambdaQuery.apply("JSON_EXTRACT( json_setting, '$."+key+"' ) = {0}", value);
                }
            });
        }

        lambdaQuery.orderByDesc(ArticleInfo::getIsTop)
                .orderByDesc(ArticleInfo::getCreateTime)
                .orderByDesc(ArticleInfo::getId);
         Page<ArticleInfo> poPage = super.page(pager,lambdaQuery);
        IPage<ArticleRes> resPage = poPage.convert(ArticleConvertor.INSTANCE::fromArticle);

//        List<ArticleRes> collect = resPage.getRecords().stream().peek(e -> {
//            String content = "";
//            if ("zh_CN".equals(finalSessionLocale.toString())) {
//                content = e.getContent();
//            } else if ("ru_RU".equals(finalSessionLocale.toString())) {
//                content = e.getRuContent();
//            } else if ("en_US".equals(finalSessionLocale.toString())) {
//                content = e.getEnContent();
//            }
//            if (Strings.isNotBlank(content)) {
//                if (Strings.isBlank(e.getDescription())) {
//                    //截取20个正文
//                    String str = content.replaceAll("<(.|\\s)*?>|\\s", "").replace("&nbsp;","").replace("\t;","").replace("\n","");
//                    if (str.length() > 20) {
//                        e.setDescription(str.substring(0, 20) + "...");
//                    } else {
//                        e.setDescription(str + "...");
//                    }
//                }
//            }
//        }).collect(Collectors.toList());
//        resPage.setRecords(collect);
        return resPage;
    }

    @Override
    public ArticleRes view(long id) throws NotFoundException {
        LambdaQueryWrapper<ArticleInfo> lambdaQuery = Wrappers.<ArticleInfo>lambdaQuery()
                .eq(ArticleInfo::getId, id)
                .eq(ArticleInfo::getStatus, StatusConst.NORMAL)
                .eq(ArticleInfo::getIsReview, ReviewConst.YES);
        ArticleInfo entity = super.getOne(lambdaQuery);
        Optional.ofNullable(entity).orElseThrow(() -> new NotFoundException("bm_article_info表中未查询到id为："+id+"的新闻"));
        // 模型转换
        ArticleRes infoRes = ArticleConvertor.INSTANCE.fromArticle(entity);
        // 处理附件
        if (StringUtils.isNotBlank(entity.getAppendix())) {
            List<FileRes> files = JSON.parseArray(entity.getAppendix(), FileRes.class);
            infoRes.setFiles(files);
        }
        // 更新阅读量
        CompletableFuture.runAsync(() -> {
            entity.setHits(entity.getHits()+1);
            super.updateById(entity);
        });
        return infoRes;
    }

    @Override
    public IPage<CountryGuideRes> getCountryGuidePageListByTitle(int currentPage, int pageSize, Long menuId, String title) {
        Page<CountryGuideRes> page = new Page<>(currentPage, pageSize);
        return super.getBaseMapper().getCountryGuidePageListByTitle(page, menuId, title);
    }

    @Override
    public IPage<CountryGuideRes> getCountryGuidePageList(int currentPage, int pageSize, Long menuId, String title) {
        Page<CountryGuideRes> page = new Page<>(currentPage, pageSize);
        return super.getBaseMapper().getCountryGuidePageList(page, menuId, title);
    }
}
