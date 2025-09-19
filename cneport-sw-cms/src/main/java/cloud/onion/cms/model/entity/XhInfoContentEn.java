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

@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@TableName(value = "shpt_info_content_en")
public class XhInfoContentEn implements Serializable {

    private static final long serialVersionUID = -7713092275422501894L;

    /**
     * 唯一主键,栏目编码+contentID
     */
    @TableId(type = IdType.AUTO, value = "ID")
    private Long id;

    /**
     * 文章ID
     */
    @TableField(value = "contentID")
    private Long contentId;

    /**
     * 对应生产系统，采编发栏目ID
     */
    @TableField(value = "cbfCatalogID")
    private String cbfCatalogID;

    /**
     * 产品栏目ID
     */
    @TableField(value = "catalogID")
    private Long catalogID;

    /**
     *
     * 内容类型:article:文章;image:图片;video:视频;audio:音频;file:文件
     */
    @TableField(value = "contentType")
    private String contentType;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 副标题
     */
    @TableField(value = "subTitle")
    private String subTitle;

    /**
     * 短标题
     */
    @TableField(value = "shortTitle")
    private String shortTitle;

    /**
     * 源标题
     */
    @TableField(value = "sourceTitle")
    private String sourceTitle;

    /**
     * 资讯内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 文章作者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 编辑
     */
    @TableField(value = "editor")
    private String editor;

    /**
     * 是否原创：0:原创 1:合作 2:转载
     */
    @TableField(value = "original")
    private Integer original;

    /**
     * 数据来源
     */
    @TableField(value = "externalSource")
    private String externalSource;

    /**
     * 语种
     */
    @TableField(value = "language")
    private String language;

    /**
     * logo文件路径
     */
    @TableField(value = "logoFile")
    private String logoFile;

    /**
     * 摘要
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 关键字
     */
    @TableField(value = "keyword")
    private String keyword;

    /**
     * 体裁
     */
    @TableField(value = "genre")
    private String genre;

    /**
     * 发布时间
     */
    @TableField(value = "publishTime")
    private Date publishTime;

    /**
     * 发布用户
     */
    @TableField(value = "publishUser")
    private String publishUser;

    /**
     * 编辑用户
     */
    @TableField(value = "modifyUser")
    private String modifyUser;

    /**
     * 编辑时间
     */
    @TableField(value = "modifyTime")
    private Date modifyTime;

    /**
     * 操作类型
     */
    @TableField(value = "operType")
    private String operType;

    @TableField(value = "recTime")
    private Date recTime;

    /**
     * 逻辑删除，1：删除；0，正常
     */
    @TableField(value = "eisDel")
    private String eisDel;
}
