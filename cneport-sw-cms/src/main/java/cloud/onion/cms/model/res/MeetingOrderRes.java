package cloud.onion.cms.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 允泽
 * @date 2022/9/29
 */
@Data
public class MeetingOrderRes implements Serializable {

    private static final long serialVersionUID = -4160533661469762583L;

    /**
     * 会议室名称
     */
    private String meetingName;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 会议议题
     */
    private String meetingTitle;

    /**
     * 会议类型
     */
    private String meetingType;

    /**
     * 会议地点
     */
    private String meetingAddress;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 参会人数
     */
    private String peoples;

    /**
     * 会议服务
     */
    private String services;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 主键
     */
    private String id;
}
