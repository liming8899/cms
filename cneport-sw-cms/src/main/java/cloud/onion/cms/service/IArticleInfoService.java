package cloud.onion.cms.service;


import cloud.onion.cms.model.entity.ArticleInfo;
import cloud.onion.cms.model.res.ArticleRes;
import cloud.onion.cms.model.res.CountryGuideRes;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.Locale;

/**
 * @author 允泽
 * @date 2022/7/16
 */
public interface IArticleInfoService extends IService<ArticleInfo> {

    /**
     * 分页获取文章
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<ArticleRes> pages(int currentPage, int pageSize, long categoryId, int isHot, String keywords, String otherSetting, Locale finalSessionLocale);

    /**
     * 获取文章信息
     * @param id
     * @return
     */
    ArticleRes view(long id) throws NotFoundException;


    /**
     * 获取国情指南信息（每个title出现一次）
     * @param currentPage
     * @param pageSize
     * @param menuId
     * @param title
     * @return
     */
    IPage<CountryGuideRes> getCountryGuidePageListByTitle(int currentPage, int pageSize, Long menuId, String title);

    /**
     * 获取国情指南信息
     * @param currentPage
     * @param pageSize
     * @param menuId
     * @param title
     * @return
     */
    IPage<CountryGuideRes> getCountryGuidePageList(int currentPage, int pageSize, Long menuId, String title);

}
