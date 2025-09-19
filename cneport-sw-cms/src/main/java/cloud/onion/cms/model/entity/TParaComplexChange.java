package cloud.onion.cms.model.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  lyn
 * @Date 2022-11-05 
 */

@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("t_para_complex_change" )
public class TParaComplexChange implements Serializable {

	/**
	 * 变更日期
	 */
   	@TableField("operate_date" )
	private Date operateDate;

	/**
	 * 变更标志(1:已变更,2:已删除,3:新增)
	 */
   	@TableField("modify_mark" )
	private String modifyMark;

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
	 * 低税率(优惠税率)
	 */
   	@TableField("low_rate" )
	private String lowRate;

	/**
	 * 法定计量单位
	 */
   	@TableField("unit_1" )
	private String unit1;

	/**
	 * 法定第二计量单位
	 */
   	@TableField("unit_2" )
	private String unit2;

	/**
	 * 监管代码
	 */
   	@TableField("control_ma" )
	private String controlMa;

	/**
	 * 品名变更(Y:已变更,N:未变更)
	 */
   	@TableField("g_name_is_modify" )
	private String gNameIsModify;

	/**
	 * 税率变更(Y:已变更,N:未变更)
	 */
   	@TableField("low_rate_is_modify" )
	private String lowRateIsModify;

	/**
	 * 法定单位变更(Y:已变更,N:未变更)
	 */
   	@TableField("unit_1_is_modify" )
	private String unit1IsModify;

	/**
	 * 第二法定变更(Y:已变更,N:未变更)
	 */
   	@TableField("unit_2_is_modify" )
	private String unit2IsModify;

	/**
	 * 监管代码变更(Y:已变更,N:未变更)
	 */
   	@TableField("control_ma_is_modify" )
	private String controlMaIsModify;

	/**
	 * 旧品名
	 */
   	@TableField("g_name_old" )
	private String gNameOld;

	/**
	 * 旧税率
	 */
   	@TableField("low_rate_old" )
	private String lowRateOld;

	/**
	 * 旧法定单位
	 */
   	@TableField("unit_1_old" )
	private String unit1Old;

	/**
	 * 旧第二单位
	 */
   	@TableField("unit_2_old" )
	private String unit2Old;

	/**
	 * 旧监管代码
	 */
   	@TableField("control_ma_old" )
	private String controlMaOld;

}
