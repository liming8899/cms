package cloud.onion.cms.model.convertor;

import cloud.onion.cms.model.entity.GeneralArticle;
import cloud.onion.cms.model.res.GeneralArticleRes;
import cloud.onion.core.base.BaseConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 允泽
 * @date 2022/8/7
 */
@Mapper
public interface GeneralArticleConvertor extends BaseConvertor<GeneralArticle, GeneralArticle, GeneralArticleRes> {

    GeneralArticleConvertor INSTANCE = Mappers.getMapper(GeneralArticleConvertor.class);
}
