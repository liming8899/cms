package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.GeneralArticle;
import cloud.onion.cms.model.res.GeneralArticleRes;
import cloud.onion.cms.model.res.SearchRes;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 允泽
 * @date 2022/8/7
 */
public interface ISearchService extends IService<GeneralArticle> {

    IPage<GeneralArticleRes> getPageList(int page, int size, String keywords);


    /**
     * @Description: 搜索文章、商品、需求
     * @Author: xuyang
     * @Date: 2022/11/9  17:56
     */
    IPage<SearchRes> getSearchPageList(int page, int size, String keywords);
}
