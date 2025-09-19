package cloud.onion.cms.model.convertor;

import cloud.onion.cms.model.entity.Consult;
import cloud.onion.cms.model.req.ConsultReq;
import cloud.onion.core.base.BaseConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 允泽
 * @date 2022/8/11
 */
@Mapper
public interface ConsultConvertor extends BaseConvertor<ConsultReq, Consult, Consult> {

    ConsultConvertor INSTANCE = Mappers.getMapper(ConsultConvertor.class);
}
