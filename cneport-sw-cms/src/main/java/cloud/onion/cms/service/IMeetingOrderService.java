package cloud.onion.cms.service;

import cloud.onion.cms.model.req.MeetingOrderReq;
import cloud.onion.cms.model.res.MeetingOrderRes;
import cloud.onion.core.entity.MeetingOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 允泽
 * @date 2022/8/11
 */
public interface IMeetingOrderService extends IService<MeetingOrder> {

    List<MeetingOrderRes> getOrdersByDate(String date,String roomId);

    List<MeetingOrderRes> getOrdersByMonth(String date, String roomId);

    void insertOne(MeetingOrderReq req);

    /**
     * @Param: id
     * @Return: Object
     * @Description: 取消预约
     * @Author: jjl
     * @Date: 2022/10/29 14:54 
     **/
    public Object cancelMeeting(String id);
}
