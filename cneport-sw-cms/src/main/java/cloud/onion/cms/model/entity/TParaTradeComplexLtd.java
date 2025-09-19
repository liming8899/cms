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
@TableName ("t_para_trade_complex_ltd" )
public class TParaTradeComplexLtd implements Serializable {

   	@TableField("oid" )
	private String oid;

	/**
	 * 商品编码
	 */
   	@TableField("code_ts" )
	private String codeTs;

	/**
	 * 商品名称
	 */
   	@TableField("g_name" )
	private String gName;

	/**
	 * 管理方式
	 */
   	@TableField("manage_style" )
	private String manageStyle;

	/**
	 * 备注
	 */
   	@TableField("remark" )
	private String remark;

   	@TableField("insert_time" )
	private java.util.Date insertTime;

   	@TableField("insert_user" )
	private String insertUser;

}
