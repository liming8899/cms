package cloud.onion.cms.model.res;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 允泽
 * @date 2022/7/29
 */
@Data
public class FileRes implements Serializable {

    private static final long serialVersionUID = 6188541735962493406L;

    private String name;

    private String url;
}
