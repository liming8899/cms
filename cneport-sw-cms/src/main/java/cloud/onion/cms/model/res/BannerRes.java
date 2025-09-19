package cloud.onion.cms.model.res;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 允泽
 * @date 2022/9/15
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class BannerRes implements Serializable {

    private static final long serialVersionUID = -6695750453324631624L;

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String picture;

    /**
     * 位置
     */
    private Integer position;

    /**
     * 图片链接
     */
    private String outLink;

    /**
     * 状态
     */
    private Integer status;
}
