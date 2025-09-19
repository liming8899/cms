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
 * @date 2022/9/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "bm_banner")
public class Banner extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8132976602452217055L;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 图片
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 位置
     */
    @TableField(value = "position")
    private Integer position;

    /**
     * 图片跳转链接
     */
    @TableField(value = "outlink")
    private String outLink;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(value = "sort")
    private Integer sort;
}
