package cloud.onion.cms.model.req;
import cloud.onion.core.annotation.EmailValid;
import cloud.onion.core.annotation.MobileValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 允泽
 * @date 2022/7/29
 */
@Data
@ApiModel
public class ConsultReq implements Serializable {

    private static final long serialVersionUID = -3580829661452817349L;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型",required = true)
    @NotBlank(message = "请选择业务类别")
    @Length(max = 40, message = "请选择正确的业务类别")
    private String type;

    /**
     * 邮箱
     */
    @ApiModelProperty("联系邮箱")
    @EmailValid(message = "请填写真实有效的联系邮箱")
    private String email;


    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @MobileValid(message = "请填写真实有效的手机号码")
    private String mobile;

    /**
     * 咨询内容
     */
    @ApiModelProperty(value = "咨询内容",required = true)
    @NotBlank(message = "请填写咨询内容")
    @Length(max = 300, message = "咨询内容不允许超过300个字")
    private String content;
}
