package cloud.onion.cms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @Description  用途
 * @Author  lyn
 * @Date 2022-11-11 
 */

@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("t_para_purpose" )
public class TParaPurpose implements Serializable {

   	@TableField("code_t_s" )
	private String codeTS;

   	@TableField("g_name" )
	private String gName;

}
