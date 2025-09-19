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
@TableName ("t_para_merch_element" )
public class TParaMerchElement implements Serializable {

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
	 * 申报要素顺序号
	 */
   	@TableField("s_num" )
	private Long sNum;

	/**
	 * 申报要素名称
	 */
   	@TableField("element" )
	private String element;

	/**
	 * 申报要素是否可为空(0:可空;1:非空)
	 */
   	@TableField("element_null" )
	private String elementNull;

	/**
	 * 更新时间
	 */
   	@TableField("update_time" )
	private java.util.Date updateTime;

	/**
	 * 更新标志(0:不变;1:新增或修改)
	 */
   	@TableField("flag" )
	private String flag;

	/**
	 * 要素名称
	 */
   	@TableField("decfacname" )
	private String decfacname;

	/**
	 * 要素类型
	 */
   	@TableField("decfactype" )
	private String decfactype;

	/**
	 * 要素可填代码
	 */
   	@TableField("decfaccode" )
	private String decfaccode;

	/**
	 * 要素可填内容
	 */
   	@TableField("decfaccontent" )
	private String decfaccontent;

}
