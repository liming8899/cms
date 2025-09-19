package cloud.onion.cms.mapper;


import cloud.onion.cms.model.entity.TParaTradeComplexBan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TParaTradeComplexBanMapper extends BaseMapper<TParaTradeComplexBan> {
    @Select(value = "<script>" +
            " select tt.* from ( SELECT t.oid,t.code_ts,t.g_name,t.remark,t.insert_time,t.insert_user, case t.manage_style when 'I' then '进口' when 'E' then '出口' else '进口，出口' end as manage_style   FROM t_para_trade_complex_ban t )tt" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(tt.code_ts,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(tt.g_name,#{keywords}) >0 </if>" +
            " order by tt.insert_time desc,tt.code_ts desc" +
            "</script>")
    IPage<TParaTradeComplexBan> getPageList(Page<TParaTradeComplexBan> pager, @Param(value = "keywords") String keywords, @Param(value = "keywords2") String keywords2);
}
