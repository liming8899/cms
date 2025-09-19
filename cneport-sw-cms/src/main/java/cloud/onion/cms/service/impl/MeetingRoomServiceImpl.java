package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.MeetingRoomImgMapper;
import cloud.onion.cms.mapper.MeetingRoomMapper;
import cloud.onion.cms.model.convertor.MeetingRoomConvertor;
import cloud.onion.cms.model.res.MeetingRoomRes;
import cloud.onion.cms.service.IMeetingRoomService;
import cloud.onion.core.constant.StatusConst;
import cloud.onion.core.entity.MeetingRoom;
import cloud.onion.core.entity.MeetingRoomImg;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 允泽
 * @date 2022/9/29
 */
@Service
public class MeetingRoomServiceImpl extends ServiceImpl<MeetingRoomMapper, MeetingRoom> implements IMeetingRoomService {

    @Autowired
    private MeetingRoomImgMapper meetingRoomImgMapper;

    @Override
    public List<MeetingRoomRes> getRooms() {
        LambdaQueryWrapper<MeetingRoom> wrapper = Wrappers.<MeetingRoom>lambdaQuery();
        wrapper.eq(MeetingRoom::getStatus, StatusConst.NORMAL);
        wrapper.orderByDesc(MeetingRoom::getId);
        List<MeetingRoom> list = super.list(wrapper);
        return list.stream().parallel().map(MeetingRoomConvertor.INSTANCE::to).collect(Collectors.toList());
    }

    @Override
    public MeetingRoomRes roomDetail(String id) {
        LambdaQueryWrapper<MeetingRoom> wrapper = Wrappers.<MeetingRoom>lambdaQuery();
        wrapper.eq(MeetingRoom::getStatus, StatusConst.NORMAL)
                .eq(MeetingRoom::getId, id);
        MeetingRoom meetingRoom = super.getOne(wrapper);
        MeetingRoomRes meetingRoomRes = new MeetingRoomRes();
        BeanUtils.copyProperties(meetingRoom, meetingRoomRes);

        LambdaQueryWrapper<MeetingRoomImg> imgWrapper = Wrappers.<MeetingRoomImg>lambdaQuery();
        imgWrapper.eq(MeetingRoomImg::getRoomId, id);
        List<MeetingRoomImg> meetingRoomImgs = meetingRoomImgMapper.selectList(imgWrapper);
        meetingRoomRes.setMeetingRoomImgList(meetingRoomImgs);
        return meetingRoomRes;
    }
}
