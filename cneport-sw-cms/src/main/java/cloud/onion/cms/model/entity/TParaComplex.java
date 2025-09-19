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
@TableName("t_para_complex" )
public class TParaComplex  implements Serializable {

	/**
	 * 商品编码
	 */
   	@TableField("code_t" )
	private String codeT;

	/**
	 * 附加商品编码
	 */
   	@TableField("code_s" )
	private String codeS;

	/**
	 * 商品名称
	 */
   	@TableField("g_name" )
	private String gName;

	/**
	 * 低税率(优惠税率,最惠国)
	 */
   	@TableField("low_rate" )
	private String lowRate;

	/**
	 * 高税率(普通税率)
	 */
   	@TableField("high_rate" )
	private String highRate;

	/**
	 * 出口税率
	 */
   	@TableField("out_rate" )
	private String outRate;

	/**
	 * tmp-REG_MARK
	 */
   	@TableField("reg_mark" )
	private String regMark;

	/**
	 * tmp-REG_RATE(消费税率)
	 */
   	@TableField("reg_rate" )
	private String regRate;

	/**
	 * tmp-TAX_TYPE
	 */
   	@TableField("tax_type" )
	private String taxType;

	/**
	 * tmp-TAX_RATE(增值税率)
	 */
   	@TableField("tax_rate" )
	private String taxRate;

	/**
	 * tmp-COMM_RATE
	 */
   	@TableField("comm_rate" )
	private String commRate;

	/**
	 * tmp-TAIWAN_RATE
	 */
   	@TableField("taiwan_rate" )
	private String taiwanRate;

	/**
	 * 其它类型
	 */
   	@TableField("other_type" )
	private String otherType;

	/**
	 * 杂费标志
	 */
   	@TableField("other_rate" )
	private String otherRate;

	/**
	 * 法定第一计量单位
	 */
   	@TableField("unit_1" )
	private String unit1;

	/**
	 * 法定第二计量单位
	 */
   	@TableField("unit_2" )
	private String unit2;

	/**
	 * 进口最低单价
	 */
   	@TableField("ilow_price" )
	private String ilowPrice;

	/**
	 * 进口最高单价
	 */
   	@TableField("ihigh_price" )
	private String ihighPrice;

	/**
	 * 出口最低单价
	 */
   	@TableField("elow_price" )
	private String elowPrice;

	/**
	 * 出口最高单价
	 */
   	@TableField("ehigh_price" )
	private String ehighPrice;

	/**
	 * 最大进口值
	 */
   	@TableField("max_in" )
	private String maxIn;

	/**
	 * 出口最大值
	 */
   	@TableField("max_out" )
	private String maxOut;

	/**
	 * 监管代码
	 */
   	@TableField("control_ma" )
	private String controlMa;

	/**
	 * 海关审核单价
	 */
   	@TableField("chk_price" )
	private String chkPrice;

	/**
	 * tmp-TARIFF_MARK 征税要求标记
	 */
   	@TableField("tariff_mark" )
	private String tariffMark;

	/**
	 * 备注
	 */
   	@TableField("note_s" )
	private String noteS;

	/**
	 * tmp-PK_SEQ
	 */
   	@TableField("pk_seq" )
	private Long pkSeq;

	/**
	 * 更新时间
	 */
   	@TableField("update_time" )
	private java.util.Date updateTime;

	/**
	 * 更新标志(0:不变;1:修改;2:新增)
	 */
   	@TableField("flag" )
	private String flag;

	/**
	 * 暂定税
	 */
   	@TableField("ten_rate" )
	private String tenRate;

	/**
	 * 完整商编
	 */
   	@TableField("code_ts" )
	private String codeTs;

}
