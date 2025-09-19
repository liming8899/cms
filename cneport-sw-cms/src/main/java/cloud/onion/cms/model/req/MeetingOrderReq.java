package cloud.onion.cms.model.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 允泽
 * @date 2022/8/11
 */
@Data
@ApiModel
public class MeetingOrderReq implements Serializable {

    private static final long serialVersionUID = 2656744706917848980L;

    /**
     * 会议室名称
     */
    @ApiModelProperty(value = "会议室名称",required = true)
    @NotBlank(message = "请输入会议室名称")
    @Length(max = 200, message = "会议室名称不能超过200个字符")
    private String meetingName;

    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人",required = true)
    @NotBlank(message = "请输入申请人名称")
    @Length(max = 200, message = "申请人名称不能超过200个字符")
    private String applicant;

    /**
     * 会议议题
     */
    @ApiModelProperty(value = "会议议题",required = true)
    @NotBlank(message = "请输入会议议题")
    @Length(max = 200, message = "会议议题不能超过200个字符")
    private String meetingTitle;

    /**
     * 会议类型
     */
    @ApiModelProperty(value = "会议类型",required = true)
    @NotBlank(message = "请输入会议类型")
    @Length(max = 200, message = "会议类型不能超过200个字符")
    private String meetingType;

    /**
     * 会议地点
     */
    @ApiModelProperty(value = "会议地点")
    @Length(max = 200, message = "会议地点不能超过200个字符")
    private String meetingAddress;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间",required = true)
    @NotNull(message = "请输入开始时间")
    @Future(message = "开始时间必须是未来的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间",required = true)
    @NotNull(message = "请输入结束时间")
    @Future(message = "结束时间必须是未来的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 参会人数
     */
    @ApiModelProperty("参会人数")
    private String peoples;

    /**
     * 会议服务
     */
    @ApiModelProperty("会议服务")
    @Length(max = 200, message = "请选择会议服务")
    private String services;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @Length(max = 200, message = "备注信息不能超过200个字符")
    private String remarks;

    /**
     * 会议室ID
     */
    @ApiModelProperty(value = "会议室ID",required = true)
    @NotBlank(message = "会议室代码不能为空")
    @Length(max = 20, message = "议室代码,必须为数字")
    private String meetingRoomId;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @Length(max = 50, message = "创建人")
    private String creator;

}
