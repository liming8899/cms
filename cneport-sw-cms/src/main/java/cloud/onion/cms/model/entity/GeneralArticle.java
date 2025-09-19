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

/**
 * @author 允泽
 * @date 2022/8/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "bm_general_article")
public class GeneralArticle extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8435622258834014219L;

    /**
     * 数据ID
     */
    @TableField(value = "data_id")
    private Long dataId;

    /**
     * 数据类型
     */
    @TableField(value = "data_type")
    private Integer dataType;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 文章标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 文章描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 添加时间
     */
    @TableField(value = "add_time")
    private Date addTime;

    /**
     * 状态1有效2无效
     */
    @TableField(value = "status")
    private Integer status;
}
