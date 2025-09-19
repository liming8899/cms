package cloud.onion.cms.model.convertor;

import cloud.onion.cms.model.req.MeetingOrderReq;
import cloud.onion.cms.model.res.MeetingOrderRes;
import cloud.onion.core.base.BaseConvertor;
import cloud.onion.core.entity.MeetingOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 允泽
 * @date 2022/8/11
 */
@Mapper
public interface MeetingOrderConvertor extends BaseConvertor<MeetingOrderReq, MeetingOrder, MeetingOrderRes> {

    MeetingOrderConvertor INSTANCE = Mappers.getMapper(MeetingOrderConvertor.class);
}
