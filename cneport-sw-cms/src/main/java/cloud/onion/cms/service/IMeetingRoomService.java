package cloud.onion.cms.service;

import cloud.onion.cms.model.res.MeetingRoomRes;
import cloud.onion.core.entity.MeetingRoom;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 允泽
 * @date 2022/9/29
 */
public interface IMeetingRoomService extends IService<MeetingRoom> {

    List<MeetingRoomRes> getRooms();

    MeetingRoomRes roomDetail(String id);
}
