package cloud.onion.cms.controller;

import cloud.onion.cms.model.req.MeetingOrderReq;
import cloud.onion.cms.service.IMeetingOrderService;
import cloud.onion.cms.service.IMeetingRoomService;
import cloud.onion.core.result.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 允泽
 * @date 2022/8/11
 */
@Controller
@RequestMapping("meeting")
@Api(tags = "会议室操作接口")
public class MeetingController {

    @Autowired
    private IMeetingOrderService meetingOrderService;
    @Autowired
    private IMeetingRoomService meetingRoomService;

    @GetMapping("room")
    @ResponseBody
    @ApiOperation(value = "展示所有会议室信息")
    public Object rooms() {
        return ResultJson.success(meetingRoomService.getRooms());
    }

    @GetMapping("roomDetail/{id}")
    @ResponseBody
    @ApiOperation(value = "展示会议室详细信息")
    public Object roomDetail(@PathVariable("id") String id) {
        return ResultJson.success(meetingRoomService.roomDetail(id));
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "预约会议室")
    public Object create(@RequestBody @Valid MeetingOrderReq req) {
        meetingOrderService.insertOne(req);
        return ResultJson.success();
    }

    @GetMapping("order")
    @ResponseBody
    @ApiOperation(value = "预定具体某天的会议室")
    public Object ordersByDay(@ApiParam(value = "预定日期，格式：yyyy-MM-dd", required = true) @RequestParam(value = "date", defaultValue = "")String date,
                              @ApiParam(value = "会议室id", required = true) @RequestParam(value = "roomId", defaultValue = "")String roomId) {
        return ResultJson.success(meetingOrderService.getOrdersByDate(date,roomId));
    }

    @GetMapping("order/month")
    @ResponseBody
    @ApiOperation(value = "预定具体某月的会议室")
    public Object ordersByMonth(@ApiParam(value = "预定日期，格式：yyyy-MM", required = true) @RequestParam(value = "date", defaultValue = "")String date,
                                @ApiParam(value = "会议室id", required = true) @RequestParam(value = "roomId", defaultValue = "")String roomId) {
        return ResultJson.success(meetingOrderService.getOrdersByMonth(date,roomId ));
    }

    /**
     * @Param: id
     * @Return: Object
     * @Description: 取消会议预约
     * @Author: jjl
     * @Date: 2022/10/29 16:21 
     **/
    @GetMapping("cancelMeeting")
    @ResponseBody
    @ApiOperation(value = "取消预定会议室")
    public Object cancelMeeting(@ApiParam(value = "会议室id", required = true) @RequestParam(value = "id", defaultValue = "")String id) {
        return meetingOrderService.cancelMeeting(id);
    }
}
