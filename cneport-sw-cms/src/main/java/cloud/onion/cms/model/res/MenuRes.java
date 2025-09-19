package cloud.onion.cms.model.res;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 允泽
 * @date 2022/7/16
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class MenuRes implements Serializable {

    private static final long serialVersionUID = 6931837477398599642L;

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 新华网ID
     */
    @TableField(value = "xh_category_id")
    private String xhCategoryId;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 访问路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 属性:1-单页，2-文章，3-外链
     */
    @TableField(value = "attribute")
    private Integer attribute;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 分类图
     */
    @TableField(value = "cover_image")
    private String coverImage;

    /**
     * 内容，属性为单页时有效
     */
    @TableField(value = "content")
    private String content;
//    /**
//     * 内容，属性为单页时有效
//     */
//    @TableField(value = "content_en")
//    private String contentEn;
//    @TableField(value = "content_ru")
//    private String contentRu;


    /**
     * 内链路径
     */
    @TableField(value = "link_path")
    private String linkPath;

    /**
     * 外链地址
     */
    @TableField(value = "link_url")
    private String linkUrl;

    /**
     * 模版
     */
    @TableField(value = "template")
    private String template;

    /**
     * 是否需要登陆授权，0-否，1-是
     */
    @TableField(value = "is_login")
    private Integer isLogin;

    /**
     * 是否显示同级菜单：0：不显示，1：显示
     */
    @TableField(value = "show_current_level")
    private Integer showCurrentLevel;

//    @TableField(value = "is_hidden")
//    private Integer isHidden;
    /**
     * 下级列表
     */
    private List<MenuRes> children;

    /**
     *
     */
    private List<MenuRes> parents;


    /**
     * 新闻
     */
    private List<ArticleRes> articleList = new ArrayList<>();

    /**
     * 国别指南
     */
    private List<CountryGuideRes> countryGuideResList = new ArrayList<>();
}
