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
@TableName("t_para_public_code_catalog" )
public class TParaPublicCodeCatalog implements Serializable {

	/**
	 * OID
	 */
   	@TableField("oid" )
	private String oid;

	/**
	 * 编码
	 */
   	@TableField("code" )
	private String code;

	/**
	 * 名称
	 */
   	@TableField("name" )
	private String name;

	/**
	 * 描述
	 */
   	@TableField("description" )
	private String description;

	/**
	 * 是否可用（1：可用 0：不可用）
	 */
   	@TableField("status" )
	private String status;

	/**
	 * 是否缓存(1为已经缓存，2为需要缓存)
	 */
   	@TableField("cache" )
	private String cache;

	/**
	 * INSERT_TIME
	 */
   	@TableField("insert_time" )
	private java.util.Date insertTime;

	/**
	 * 数据来源（0固定，1电子口岸，2爬网，3组合）
	 */
   	@TableField("source" )
	private String source;

	/**
	 * 数据位置(T_PARA_PUBLIC_CODE_ITEM，其他）
	 */
   	@TableField("data_flag" )
	private String dataFlag;

	/**
	 * 版本号
	 */
   	@TableField("etag" )
	private String etag;

	/**
	 * 修改时间
	 */
   	@TableField("update_time" )
	private java.util.Date updateTime;

	/**
	 * 查询SQL语句
	 */
   	@TableField("sql1" )
	private String sql1;

	/**
	 * 是否显示map节点
	 */
   	@TableField("isshow" )
	private String isshow;

}
