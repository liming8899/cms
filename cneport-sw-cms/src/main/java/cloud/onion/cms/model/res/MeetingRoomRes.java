package cloud.onion.cms.model.res;

import cloud.onion.core.entity.MeetingRoomImg;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 允泽
 * @date 2022/9/29
 */
@Data
public class MeetingRoomRes implements Serializable {

    private static final long serialVersionUID = -5105040317408225100L;

    private Long id;

    private String name;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private Integer status;

    private String serviceItem;

    private String peopleNum;

    private String receiveEmail;

    private String envDetail;

    private String envDetailEn;

    private String envDetailRu;

    private List<MeetingRoomImg> meetingRoomImgList;
}
