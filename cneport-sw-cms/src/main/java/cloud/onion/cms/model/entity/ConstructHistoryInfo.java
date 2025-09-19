package cloud.onion.cms.model.entity;


import cloud.onion.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "bm_construct_history")
public class ConstructHistoryInfo extends BaseEntity implements Serializable {

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;
    @TableField(value = "en_title")
    private String enTitle;
    @TableField(value = "ru_title")
    private String ruTitle;

    /**
     * 封面图片
     */
    @TableField(value = "cover_image")
    private String coverImage;

    /**
     * 文章描述
     */
    @TableField(value = "description")
    private String description;
    @TableField(value = "en_description")
    private String enDescription;
    @TableField(value = "ru_description")
    private String ruDescription;


    /**
     * 历程时间
     */
    @TableField(value = "history_time")
    private Date historyTime;


    @TableField(exist = false)
    private String historyTimeStr;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 审核标识
     */
    @TableField(value = "is_review")
    private Integer isReview;

    /**
     * 拒绝理由
     */
    @TableField(value = "refusal_reason")
    private String refusalReason;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 审核人ID
     */
    @TableField(value = "reviewer_id")
    private Long reviewerId;

    /**
     * 审核时间
     */
    @TableField(value = "review_time")
    private Date reviewTime;

}
