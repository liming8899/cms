package cloud.onion.cms.model.convertor;


import cloud.onion.cms.model.entity.Page;
import cloud.onion.cms.model.res.PageRes;
import cloud.onion.core.base.BaseConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 允泽
 * @date 2022/9/5
 */
@Mapper
public interface PageConvertor extends BaseConvertor<Page, Page, PageRes> {

    PageConvertor INSTANCE = Mappers.getMapper(PageConvertor.class);
}
