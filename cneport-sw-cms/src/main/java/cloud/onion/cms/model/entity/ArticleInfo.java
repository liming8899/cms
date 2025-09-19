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
 * @date 2022/7/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "bm_article_info")
public class ArticleInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1706774835124971950L;

    /**
     * 分类ID
     */
    @TableField(value = "category_id")
    private Long categoryId;

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
     * 封面图
     */
    @TableField(value = "cover_image")
    private String coverImage;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    @TableField(value = "en_description")
    private String enDescription;


    @TableField(value = "ru_description")
    private String ruDescription;


    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    @TableField(value = "en_content")
    private String enContent;


    @TableField(value = "ru_content")
    private String ruContent;


    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 来源
     */
    @TableField(value = "source")
    private String source;
    @TableField(value = "en_source")
    private String enSource;
    @TableField(value = "ru_source")
    private String ruSource;
    /**
     * 是否置顶，1-否，2-是
     */
    @TableField(value = "is_top")
    private Integer isTop;

    /**
     * 是否热搜，1-否，2-是
     */
    @TableField(value = "is_hot_search")
    private Integer isHotSearch;

    /**
     * 是否审核，1-否，2-是
     */
    @TableField(value = "is_review")
    private Integer isReview;

    /**
     * 浏览数
     */
    @TableField(value = "hits")
    private Integer hits;

    /**
     * 状态：1-正常，2-显示
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 附件信息
     */
    @TableField(value = "appendix")
    private String appendix;

    @TableField(value = "is_link")
    private Integer isLink;

    @TableField(value = "link_url")
    private String linkUrl;
}
