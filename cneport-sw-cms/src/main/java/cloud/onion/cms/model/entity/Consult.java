package cloud.onion.cms.model.entity;

import cloud.onion.core.base.BaseEntity;
import cloud.onion.core.handler.EncryptHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 允泽
 * @date 2022/7/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "bm_consult_info", autoResultMap = true)
public class Consult extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2948626611247694562L;

    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 手机号码
     */
    @TableField(value = "mobile", typeHandler = EncryptHandler.class)
    private String mobile;

    /**
     * 咨询内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * ip地址
     */
    @TableField(value = "ip_address")
    private String ipAddress;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

}
