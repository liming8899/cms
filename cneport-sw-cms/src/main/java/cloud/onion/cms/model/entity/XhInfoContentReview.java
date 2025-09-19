package cloud.onion.cms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 允泽
 * @date 2022/7/28
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@TableName(value = "shpt_info_content_review")
public class XhInfoContentReview implements Serializable {

    private static final long serialVersionUID = -7713092275422501894L;

    /**
     * 唯一主键
     */
    @TableId(type = IdType.AUTO, value = "ID")
    private Long id;

    /**
     * 文章ID
     */
    @TableField(value = "content_id")
    private Long contentId;

    /**
     * 审核状态，1-审核通过，2-审核未通过
     */
    @TableField(value = "review_status")
    private Integer reviewStatus;

    /**
     * 拒绝理由
     */
    @TableField(value = "review_reason")
    private String reviewReason;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

}
