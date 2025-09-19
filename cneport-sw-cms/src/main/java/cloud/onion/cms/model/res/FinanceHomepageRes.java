package cloud.onion.cms.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FinanceHomepageRes implements Serializable {

    private static final long serialVersionUID = 3749919237278502521L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 标题
     */
    private String name;
    private String enName;
    private String ruName;

    /**
     * 排序ID，id越大越靠前
     */
    private Integer sort;

    /**
     * 是否置顶：0-不置顶，1-置顶
     */
    private Integer isTop;

    /**
     * 显示状态
     */
    private Integer status;

    /**
     * 审核标识：1-待审，2-审核通过，3-审核未通过
     */
    private Integer isReview;

    /**
     * 拒绝理由
     */
    private String refusalReason;

    private List<FinanceAgencyRes> financeAgencyResList;

}
