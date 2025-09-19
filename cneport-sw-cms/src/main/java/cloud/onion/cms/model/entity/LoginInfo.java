package cloud.onion.cms.model.entity;

import cloud.onion.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 允泽
 * @date 2022/9/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "visit_log_info")
public class LoginInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8132976605676517055L;

    /**
     * 登录账号
     */
    @TableField(value = "login_account")
    private String loginAccount;

    /**
     * 登录人姓名
     */
    @TableField(value = "username")
    private String username;
    /**
     * 访问具体id
     */
    @TableField(value = "visit_id")
    private Long visitId;
    /**
     * 访问具体名称
     */
    @TableField(value = "visit_name")
    private String visitName;
    /**
     * 访问日期
     */
    @TableField(value = "visit_time")
    private Date visitTime;

    /**
     * 访问模块
     */
    @TableField(value = "visit_moudle")
    private String visitMoudle;

    /**
     * 查询条件
     */
    @TableField(value = "search_word")
    private String searchWord;

    /**
     * 从何处进入链接
     */
    @TableField(value = "from_link")
    private String fromLink;

    /**
     * 进入后的链接
     */
    @TableField(value = "to_link")
    private String toLink;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "ip_address")
    private String ipAddress;
    /**
     * 国家
     */
    @TableField(value = "country")
    private String country;
    /**
     * 省份
     */
    @TableField(value = "region")
    private String region;
    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

}
