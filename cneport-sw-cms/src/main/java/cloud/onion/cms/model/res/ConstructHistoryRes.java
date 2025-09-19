package cloud.onion.cms.model.res;

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
public class ConstructHistoryRes implements Serializable {


    /**
     * 数据ID
     */
    private Long id;


    /**
     * 标题
     */
    private String title;
    private String enTitle;
    private String ruTitle;

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 文章描述
     */
    private String description;
    private String enDescription;
    private String ruDescription;


    /**
     * 历程时间
     */
    private Date historyTime;

    /**
     * 历程时间
     */
    private String historyTimeStr;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 状态
     */
    private Integer status;

}
