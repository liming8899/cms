package cloud.onion.cms.model.entity;

import cloud.onion.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 允泽
 * @date 2022/7/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "bm_link_info")
public class Link extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5245204463807820945L;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 链接名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 链接url地址
     */
    @TableField(value = "link_url")
    private String linkUrl;

    /**
     * 状态：1-显示，2-隐藏
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 深度
     */
    @TableField(value = "deep")
    private Integer deep;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;
}
