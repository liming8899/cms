package cloud.onion.cms.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 允泽
 * @date 2022/8/7
 */
@Data
public class GeneralArticleRes implements Serializable {

    private static final long serialVersionUID = 1009120834774506445L;

    /**
     * 数据ID
     */
    private String dataId;

    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 文章标题
     */
    private String title;
    private String enTitle;
    private String ruTitle;

    /**
     * 文章描述
     */
    private String description;
    private String enDescription;
    private String ruDescription;

    /**
     * 添加时间
     */
    private Date addTime;

    private Integer status;
}
