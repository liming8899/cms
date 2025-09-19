package cloud.onion.cms.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 允泽
 * @date 2022/7/22
 */
@Data
public class ArticleQuery implements Serializable {

    private static final long serialVersionUID = 5374341195181714073L;

    private Integer currentPage;

    private Integer pageSize;

    private Long categoryId;

    private String keywords;

    private Integer isHot;


}
