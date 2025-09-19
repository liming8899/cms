package cloud.onion.cms.model.convertor;

import cloud.onion.cms.model.res.MeetingRoomRes;
import cloud.onion.core.base.BaseConvertor;
import cloud.onion.core.entity.MeetingRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 允泽
 * @date 2022/9/29
 */
@Mapper
public interface MeetingRoomConvertor extends BaseConvertor<MeetingRoom, MeetingRoom, MeetingRoomRes> {

    MeetingRoomConvertor INSTANCE = Mappers.getMapper(MeetingRoomConvertor.class);
}
