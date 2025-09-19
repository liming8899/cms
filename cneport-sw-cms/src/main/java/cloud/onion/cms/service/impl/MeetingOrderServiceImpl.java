package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.MeetingOrderMapper;
import cloud.onion.cms.mapper.MeetingRoomMapper;
import cloud.onion.cms.model.convertor.MeetingOrderConvertor;
import cloud.onion.cms.model.req.MeetingOrderReq;
import cloud.onion.cms.model.res.MeetingOrderRes;
import cloud.onion.cms.service.IMeetingOrderService;
import cloud.onion.core.entity.MeetingOrder;
import cloud.onion.core.entity.MeetingRoom;
import cloud.onion.core.exception.BizException;
import cloud.onion.core.result.ResultCode;
import cloud.onion.core.result.ResultJson;
import cloud.onion.core.utils.ContextUtil;
import cloud.onion.core.utils.DateUtil;
import cloud.onion.core.utils.IpAddressUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author 允泽
 * @date 2022/8/11
 */
@Service
public class MeetingOrderServiceImpl extends ServiceImpl<MeetingOrderMapper, MeetingOrder> implements IMeetingOrderService {
    @Autowired
    private MeetingRoomMapper meetingRoomMapper;
    @Autowired
    private Executor threadPoolTaskExecutor;

    @Override
    public List<MeetingOrderRes> getOrdersByDate(String date,String roomId) {
        if (!StpUtil.isLogin()) {
            throw new BizException("请先登录");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date datetime = null;
        try {
            datetime = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new BizException(e.getMessage());
        }
        LocalDateTime localDateTime = datetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime startTime = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
        LambdaQueryWrapper<MeetingOrder> wrapper = Wrappers.<MeetingOrder>lambdaQuery();
        wrapper.between(MeetingOrder::getStartTime, startTime, endTime);
        if(StringUtils.isNotBlank(roomId)){
            wrapper.eq(MeetingOrder::getMeetingRoomId,roomId);
        }
        wrapper.orderByAsc(MeetingOrder::getStartTime);
        List<MeetingOrder> list = super.list(wrapper);
        return list.stream().parallel().map(MeetingOrderConvertor.INSTANCE::to).collect(Collectors.toList());
    }

    @Override
    public List<MeetingOrderRes> getOrdersByMonth(String date, String roomId) {
        if (!StpUtil.isLogin()) {
            throw new BizException("请先登录");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date datetime = null;
        try {
            datetime = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new BizException(e.getMessage());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        LocalDateTime startTime = DateUtil.getMonthStartTime(year, month);
        LocalDateTime endTime = DateUtil.getMonthEndTime(year, month);
        LambdaQueryWrapper<MeetingOrder> wrapper = Wrappers.<MeetingOrder>lambdaQuery();
        if (StringUtils.isNotBlank(roomId)) {
            wrapper.eq(MeetingOrder::getMeetingRoomId, roomId);
        }
        wrapper.between(MeetingOrder::getStartTime, startTime, endTime);
        wrapper.orderByAsc(MeetingOrder::getStartTime);
        List<MeetingOrder> list = super.list(wrapper);
        return list.stream().parallel().map(MeetingOrderConvertor.INSTANCE::to).collect(Collectors.toList());
    }

    @Override
    public void insertOne(MeetingOrderReq req) {
        if (!StpUtil.isLogin()) {
            throw new BizException("请先登录");
        }
        String ipAddress = IpAddressUtil.getIpAddr(ContextUtil.getRequest());
        LambdaQueryWrapper<MeetingOrder> wrapper = Wrappers.<MeetingOrder>lambdaQuery();
        wrapper.eq(MeetingOrder::getMeetingRoomId, req.getMeetingRoomId())
            .ge(MeetingOrder::getStartTime,req.getStartTime())
            .le(MeetingOrder::getEndTime, req.getEndTime());
        long count = super.count(wrapper);
        if (count > 0) {
            throw new BizException("当前时间段约满，请选择其他时间段");
        }
        req.setCreator(StpUtil.getLoginId()+"");
        MeetingOrder entity = MeetingOrderConvertor.INSTANCE.from(req);
        if(StringUtils.isBlank(entity.getMeetingAddress())){
            entity.setMeetingAddress(entity.getMeetingName());
        }
        entity.setIpAddress(ipAddress);
        boolean result = super.save(entity);
        if (!result) {
            throw new BizException(ResultCode.INSERT_FAIL);
        } else {
            Object username = StpUtil.getExtra("username");
            threadPoolTaskExecutor.execute(() -> {
                try {
                    MeetingRoom meetingRoom = meetingRoomMapper.selectById(req.getMeetingRoomId());
                    String contentAll = "系统用户" + username + "预定了一场会议，<br/>时间：" +
                            DateUtil.DateToStr(req.getStartTime()) + "到" + DateUtil.DateToStr(req.getEndTime()) +
                            "<br/>会议室：" + req.getMeetingName() +
                            "<br/>会议议题：" + req.getMeetingTitle() +
                            "<br/>会议类型：" + req.getMeetingType() +
                            "<br/>参会人数：" + req.getPeoples() + "人" +
                            "<br/>会议服务：" + req.getServices() +
                            "<br/>备注：" + req.getRemarks();
                    MailUtil.send(meetingRoom.getReceiveEmail(), "会议室预定", contentAll, true);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
        }
    }

    @Override
    public Object cancelMeeting(String id) {
        if(StringUtils.isBlank(id)){
            return ResultJson.fail("请求数据异常！");
        }
        MeetingOrder meetingOrder = super.getById(id);
        if(StringUtils.isBlank(meetingOrder.getCreator()) || !meetingOrder.getCreator().equals(StpUtil.getLoginId())){
            return ResultJson.fail("非当前用户创建，不可取消！");
        }
        boolean result = super.removeById(meetingOrder);
        if (result) {
            Object username = StpUtil.getExtra("username");
            threadPoolTaskExecutor.execute(() -> {
                MeetingRoom meetingRoom = meetingRoomMapper.selectById(meetingOrder.getMeetingRoomId());
                String contentAll = "系统用户" + username + "取消了" +
                        DateUtil.DateToStr(meetingOrder.getStartTime()) + "到" + DateUtil.DateToStr(meetingOrder.getEndTime())
                        + "于" + meetingOrder.getMeetingName() +"的会议";
                MailUtil.send(meetingRoom.getReceiveEmail(), "会议室预定取消", contentAll, true);
            });
        }
        return ResultJson.success();
    }
}
