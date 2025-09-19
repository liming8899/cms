package cloud.onion.cms.model.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName ("t_para_merch_ele_cmp_detail" )
public class TParaMerchEleCmpDetail implements Serializable {

	/**
	 * SID
	 */
   	@TableField("sid" )
	private String sid;

	/**
	 * 变更日期
	 */
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
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
	 * 比对结果
	 */
   	@TableField("compare_result" )
	private String compareResult;

	/**
	 * 插入时间
	 */
   	@TableField("insert_time" )
	private java.util.Date insertTime;

	/**
	 * 新要素序号
	 */
   	@TableField("s_num" )
	private Long sNum;

	/**
	 * 旧要素序号
	 */
   	@TableField("s_num_old" )
	private Long sNumOld;

}
