package cloud.onion.cms.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 允泽
 * @date 2022/8/7
 */
@Data
public class SearchRes implements Serializable {

    private static final long serialVersionUID = 1009120834774506445L;

    /**
     * 数据ID
     */
    private String id;


    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 搜索标题(文章+商品+需求)
     */
    private String title;
    private String enTitle;
    private String ruTitle;

    /**
     * 搜索描述
     */
    private String description;
    private String enDescription;
    private String ruDescription;

    /**
     * 添加时间
     */
    private Date addTime;

    private Integer status;

    private Integer order_num;

    /**
     * 1是文章 2是商品 3是需求
     */
    private Integer type;

    private Integer deleted;

    private Integer dataType;
}
