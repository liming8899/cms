package cloud.onion.cms.model.res;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 允泽
 * @date 2022/7/15
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class SiteConfigRes implements Serializable {

    private static final long serialVersionUID = -3384003495387117577L;

    /**
     * 站点标题
     */
    private String title;

    /**
     * 站点关键词
     */
    private String keywords;

    /**
     * 站点描述
     */
    private String description;

    private String copyright;
}
