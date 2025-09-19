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
@TableName(value = "bm_site_config")
public class SiteConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1585623766909210643L;

    /**
     * 站点标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 站点关键词
     */
    @TableField(value = "keywords")
    private String keywords;

    /**
     * 站点描述
     */
    @TableField(value = "description")
    private String description;

    @TableField(value = "copyright")
    private String copyright;
}
