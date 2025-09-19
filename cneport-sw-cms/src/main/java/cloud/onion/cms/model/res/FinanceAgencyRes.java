package cloud.onion.cms.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class FinanceAgencyRes implements Serializable {

    private Long id;

    private String name;
    private String enName;
    private String ruName;
    /**
     * 机构ID
     */
    private Long homepageId;
    /**
     * 机构名称
     */
    private String homepageName;

    /**
     * logo地址
     */
    private String logo;
    /**
     * 机构描述
     */
    private String description;
    private String enDescription;
    private String ruDescription;
    /**
     * 是否置顶：0-不置顶，1-置顶
     */
    private Integer isTop;
    /**
     * 是否外链：0-不是外链，1-是外链
     */
    private Integer isExternalLink;

    /**
     * 外链地址
     */
    private String externalLinkAddress;

    /**
     * 单页内容
     */
    private String content;
    private String enContent;
    private String ruContent;

    /**
     * 排序ID，id越大越靠前
     */
    private Integer sort;
    /**
     * 可用性（0：不可用；1：可用）
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private List<FinanceProductRes> financeProductResList;

}
