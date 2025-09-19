package cloud.onion.cms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Description  
 * @Author  lyn
 * @Date 2022-11-08 
 */

@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("t_para_entry_hscode" )
public class TParaEntryHscode  {

	/**
	 * OID
	 */
   	@TableField("oid" )
	private String oid;

	/**
	 * 商品编码
	 */
   	@TableField("code_t_s" )
	private String codeTS;

	/**
	 * 商品名称
	 */
   	@TableField("g_name" )
	private String gName;

	/**
	 * 规格型号（在申报要素里叫申报要素）
	 */
   	@TableField("element" )
	private String element;

	/**
	 * 入库时间
	 */
   	@TableField("inserttime" )
	private java.util.Date inserttime;

   	@TableField("year_month1" )
	private String yearMonth1;

}
