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
@TableName("t_para_exchrate" )
public class TParaExchrate  implements Serializable {

   	@TableField("curr_code" )
	private String currCode;

   	@TableField("begin_date" )
	private java.util.Date beginDate;

   	@TableField("end_date" )
	private java.util.Date endDate;

   	@TableField("t_rate" )
	private Double tRate;

   	@TableField("rmb_rate" )
	private Double rmbRate;

   	@TableField("usd_rate" )
	private Double usdRate;

   	@TableField("today_rate" )
	private Double todayRate;

   	@TableField("last_modify_date" )
	private java.util.Date lastModifyDate;
	private String years;
}
