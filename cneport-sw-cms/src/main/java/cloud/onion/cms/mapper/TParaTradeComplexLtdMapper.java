package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TParaTradeComplexLtd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface TParaTradeComplexLtdMapper extends BaseMapper<TParaTradeComplexLtd> {


    @Select(value = "<script>" +
            "select t.oid,t.code_ts,t.g_name,case t.manage_style when 'I' then '进口' when 'E' then '出口' ELSE '' end as manage_style ," +
            "remark,insert_time,insert_user from `t_para_trade_complex_ltd` t " +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.code_ts,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.g_name,#{keywords}) >0 </if>" +
            "order by t.code_ts " +
            "</script>")
    IPage<Map<Object, String>> getPageList(Page<Map<Object, String>> pager, @Param(value = "keywords")String keywords, @Param(value = "keywords2")String keywords2);
}
