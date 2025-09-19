package cloud.onion.cms.model.entity;

import cloud.onion.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 允泽
 * @date 2022/9/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "bm_page_info")
public class Page extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4211165088658499257L;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 页面内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 访问路径
     */
    @TableField(value = "access_path")
    private String accessPath;

    /**
     * 类型：1-主站，2-B2B
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 状态：1-正常，2-隐藏
     */
    @TableField(value = "status")
    private Integer status;
}
