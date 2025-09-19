package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.GeneralArticleMapper;
import cloud.onion.cms.model.entity.GeneralArticle;
import cloud.onion.cms.model.res.GeneralArticleRes;
import cloud.onion.cms.model.res.SearchRes;
import cloud.onion.cms.service.ISearchService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 允泽
 * @date 2022/8/7
 */
@Service
public class SearchServiceImpl extends ServiceImpl<GeneralArticleMapper, GeneralArticle> implements ISearchService {
    @Override
    public IPage<GeneralArticleRes> getPageList(int page, int size, String keywords) {
        Page<GeneralArticleRes> pager = new Page<>(page, size);
        IPage<GeneralArticleRes> pageList = super.getBaseMapper().getPageList(pager, keywords);
        return pageList;
    }


    /**
     * @Description: 搜索文章、商品、需求
     * @Author: xuyang
     * @Date: 2022/11/9  17:56
     */
    @Override
    public IPage<SearchRes> getSearchPageList(int page, int size, String keywords) {
        Page<SearchRes> pager = new Page<>(page, size);
        IPage<SearchRes> pageList = super.getBaseMapper().getSeachPageList(pager, keywords);
        return pageList;
    }
}
