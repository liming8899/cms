package cloud.onion.cms.model.entity;

import cloud.onion.core.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
/**
 * @Description  
 * @Author  lyn
 * @Date 2022-11-07 
 */

@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("t_para_3c_cert" )
public class TPara3CCert  implements Serializable {

	/**
	 * HS编码
	 */
   	@TableField("code_t_s" )
	private String codeTS;

	/**
	 * 商品名称及备注
	 */
   	@TableField("g_name" )
	private String gName;

	/**
	 * 产品类别
	 */
   	@TableField("product_category" )
	private String productCategory;

	/**
	 * 强制认证
	 */
   	@TableField("compulsory_certification" )
	private String compulsoryCertification;

	/**
	 * 入库时间
	 */
   	@TableField("insert_time" )
	private java.util.Date insertTime;

	/**
	 * 适用范围
	 */
   	@TableField("suit_range" )
	private String suitRange;

	/**
	 * 认证标准
	 */
   	@TableField("certification_standards" )
	private String certificationStandards;

	/**
	 * 对适用产品的描述和列举
	 */
   	@TableField("description_enumeration" )
	private String descriptionEnumeration;

}
