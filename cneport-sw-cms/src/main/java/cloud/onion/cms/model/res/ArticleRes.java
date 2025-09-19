package cloud.onion.cms.model.res;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 允泽
 * @date 2022/7/16
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class ArticleRes implements Serializable {

    private static final long serialVersionUID = -7769416706716888093L;

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 标题
     */
    private String title;

    private String enTitle;

    private String ruTitle;

    /**
     * 封面图
     */
    private String coverImage;

    /**
     * 描述
     */
    private String description;

    private String enDescription;

    private String ruDescription;

    /**
     * 内容
     */
    private String content;

    private String enContent;

    private String ruContent;

    /**
     * 作者
     */
    private String author;

    /**
     * 来源
     */
    private String source;
    private String enSource;
    private String ruSource;

    /**
     * 浏览数
     */
    private Integer hits;

    /**
     * 附件列表
     */
    private List<FileRes> files;

    /**
     * 创建时间
     */
    private Date createTime;

    private Integer isLink;

    private String linkUrl;
}
