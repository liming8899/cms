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
@TableName("consume_standard" )
public class ConsumeStandard implements Serializable {

	/**
	 * 主键
	 */
   	@TableField("id" )
	private String id;

	/**
	 * 单耗标准唯一号
	 */
   	@TableField("rec_num" )
	private String recNum;

	/**
	 * 成品编码
	 */
   	@TableField("ex_code_ts" )
	private String exCodeTs;

	/**
	 * 料件编码
	 */
   	@TableField("im_code_ts" )
	private String imCodeTs;

	/**
	 * 成品名称
	 */
   	@TableField("ex_g_name" )
	private String exGName;

	/**
	 * 成品规格型号
	 */
   	@TableField("ex_g_model" )
	private String exGModel;

	/**
	 * 成品计量单位编码
	 */
   	@TableField("ex_g_unit" )
	private String exGUnit;

	/**
	 * 料件名称
	 */
   	@TableField("im_g_name" )
	private String imGName;

	/**
	 * 料件规格型号
	 */
   	@TableField("im_g_model" )
	private String imGModel;

	/**
	 * 料件计量单位
	 */
   	@TableField("im_g_unit" )
	private String imGUnit;

	/**
	 * 最大单耗
	 */
   	@TableField("max_consume" )
	private Double maxConsume;

	/**
	 * 最大损耗
	 */
   	@TableField("max_damage" )
	private Double maxDamage;

	/**
	 * 单耗版本
	 */
   	@TableField("consume_ver" )
	private String consumeVer;

	/**
	 * 单耗标准编码
	 */
   	@TableField("consume_hdb" )
	private String consumeHdb;

	/**
	 * 料件幅宽
	 */
   	@TableField("im_width" )
	private Long imWidth;

	/**
	 * 备注
	 */
   	@TableField("note_s" )
	private String noteS;

	/**
	 * 状态 1暂存 2提交  3审核通过
	 */
   	@TableField("status" )
	private String status;

	/**
	 * 创建时间
	 */
   	@TableField("create_date" )
	private java.util.Date createDate;

	/**
	 * 创建用户
	 */
   	@TableField("create_userid" )
	private String createUserid;

	/**
	 * 更新时间
	 */
   	@TableField("lastupdate" )
	private java.util.Date lastupdate;

	/**
	 * 更新用户
	 */
   	@TableField("lastupdate_userid" )
	private String lastupdateUserid;

	/**
	 * 0无效数据   1有效数据。
	 */
   	@TableField("activation" )
	private Long activation;

	/**
	 * 料件规格型号长度
	 */
   	@TableField("im_code_ts_len" )
	private Long imCodeTsLen;

	/**
	 * 成品规格型号长度
	 */
   	@TableField("ex_code_ts_len" )
	private Long exCodeTsLen;

	/**
	 * 单耗标准类型 1标准 0 参数
	 */
   	@TableField("cs_type" )
	private Long csType;

	/**
	 * 成品的最大单耗
	 */
   	@TableField("exg_max_consume" )
	private Double exgMaxConsume;

	/**
	 * 成品的最小单耗
	 */
   	@TableField("exg_min_consume" )
	private Double exgMinConsume;

	/**
	 * 成品的最大损耗
	 */
   	@TableField("exg_max_damage" )
	private Double exgMaxDamage;

	/**
	 * 成品的最小损耗
	 */
   	@TableField("exg_min_damage" )
	private Double exgMinDamage;

	/**
	 * 外部单耗标准链接
	 */
   	@TableField("link_consume_hdb" )
	private String linkConsumeHdb;

}
