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
@TableName(value = "shpt_info_resource")
public class XhInfoResource implements Serializable {
    private static final long serialVersionUID = -3685663861417125449L;

    /**
     * 唯一主键
     */
    @TableId(type = IdType.AUTO, value = "ID")
    private String id;

    /**
     * 文章ID
     */
    @TableField(value = "contentID")
    private Long contentID;

    /**
     * 附件类型
     */
    @TableField(value = "resType")
    private String resType;

    /**
     * 原始名称
     */
    @TableField(value = "originalName")
    private String originalName;

    /**
     * 文件名称
     */
    @TableField(value = "fileName")
    private String fileName;

    /**
     * 文件大小
     */
    @TableField(value = "fileSize")
    private Integer fileSize;

    /**
     * 文件路径
     */
    @TableField(value = "attachUrl")
    private String attachUrl;

    /**
     * 数据接收时间
     */
    @TableField(value = "rectime")
    private Date rectime;

    /**
     * 编辑时间
     */
    @TableField(value = "modifyTime")
    private Date modifyTime;

    /**
     * 逻辑删除：1，删除；0 正常
     */
    @TableField(value = "eisDel")
    private String eisDel;
}
