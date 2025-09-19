package cloud.onion.cms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description  
 * @Author  lyn
 * @Date 2022-11-05 
 */

@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("t_para_complex_ban" )
public class TParaComplexBan implements Serializable {

   	@TableField("code" )
	private String code;

   	@TableField("name" )
	private String name;

   	@TableField("banlist" )
	private String banlist;

}
