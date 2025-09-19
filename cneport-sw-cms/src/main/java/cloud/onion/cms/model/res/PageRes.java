package cloud.onion.cms.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 允泽
 * @date 2022/9/5
 */
@Data
public class PageRes implements Serializable {

    private static final long serialVersionUID = -2466407255440354188L;

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 页面内容
     */
    private String content;

    /**
     * 访问路径
     */
    private String accessPath;

    /**
     * 类型：1-主站，2-B2B
     */
    private Integer type;

    /**
     * 状态：1-正常，2-隐藏
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
}
