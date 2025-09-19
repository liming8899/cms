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
@TableName("t_para_public_code_item")
public class TParaPublicCodeItem implements Serializable {

	/**
	 * OID
	 */
   	@TableField("oid")
	private String oid;

	/**
	 * 父ID（本表递归使用）
	 */
   	@TableField("parent_oid")
	private String parentOid;

	/**
	 * 编码
	 */
   	@TableField("code")
	private String code;

	/**
	 * 名称
	 */
   	@TableField("name")
	private String name;

	/**
	 * 描述
	 */
   	@TableField("description")
	private String description;

	/**
	 * 英文名称
	 */
   	@TableField("name_en")
	private String nameEn;

	/**
	 * 是否可用（1：可用 0：不可用）
	 */
   	@TableField("status")
	private String status;

	/**
	 * 顺序号
	 */
   	@TableField("order_no")
	private Double orderNo;

	/**
	 * 类别OID
	 */
   	@TableField("catalog_oid")
	private String catalogOid;

	/**
	 * INSERT_TIME
	 */
   	@TableField("insert_time")
	private java.util.Date insertTime;

	/**
	 * 币制代码中英文代码
	 */
	@TableField(exist = false)
	private String codeEn;
}
