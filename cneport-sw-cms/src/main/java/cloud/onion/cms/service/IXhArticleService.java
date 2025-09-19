package cloud.onion.cms.service;


import cloud.onion.cms.model.entity.XhInfoContent;
import cloud.onion.cms.model.res.ArticleRes;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.javassist.NotFoundException;

/**
 * @author 允泽
 * @date 2022/8/1
 */

public interface IXhArticleService extends IService<XhInfoContent> {

    IPage<ArticleRes> getPageList(int page, int size, Long categoryId);

    ArticleRes getByPrimaryId(Long id) throws NotFoundException;

    String getImageByCatalogId(Long id, String titleContent);
}
