package cloud.onion.cms.model.res;

import cloud.onion.core.base.TreeNode;
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
@EqualsAndHashCode(callSuper = true)
@ToString
@Accessors(chain = true)
public class LinkRes extends TreeNode implements Serializable {

    private static final long serialVersionUID = -8904470230929957834L;


    /**
     * 链接url地址
     */
    private String linkUrl;
}
