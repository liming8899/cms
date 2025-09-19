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
@TableName ("t_para_merch_ele_cmp_result" )
public class TParaMerchEleCmpResult implements Serializable {

	/**
	 * SID
	 */
   	@TableField("sid" )
	private String sid;

	/**
	 * 变更日期
	 */
   	@TableField("change_date" )
	private String changeDate;

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
	 * 新申报要素
	 */
   	@TableField("element" )
	private String element;

	/**
	 * 旧申报要素
	 */
   	@TableField("element_old" )
	private String elementOld;

	/**
	 * 插入时间
	 */
   	@TableField("insert_time" )
	private java.util.Date insertTime;

	/**
	 * 变更内容{新增;删减;调序}
	 */
   	@TableField("change_result" )
	private String changeResult;

}
