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
@TableName ("t_para_trade_complex_ban" )
public class TParaTradeComplexBan implements Serializable {

   	@TableField("oid" )
	private String oid;

   	@TableField("code_ts" )
	private String codeTs;

   	@TableField("g_name" )
	private String gName;

   	@TableField("manage_style" )
	private String manageStyle;

   	@TableField("remark" )
	private String remark;

   	@TableField("insert_time" )
	private java.util.Date insertTime;

   	@TableField("insert_user" )
	private String insertUser;

}
