package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TParaComplex;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface TParaComplexMapper extends BaseMapper<TParaComplex> {
    @Select(value = "<script>" +
            " select t.*,a.name as unit_name_1,b.name as unit_name_2,d.back_rate from  `t_para_complex` t " +
            " left join ( SELECT i.* from `t_para_public_code_catalog` c inner join `t_para_public_code_item` i  on c.oid=i.catalog_oid  where c.code='UNIT')a on t.unit_1=a.code "+
            " left join ( SELECT i.* from `t_para_public_code_catalog` c inner join `t_para_public_code_item` i  on c.oid=i.catalog_oid  where c.code='UNIT')b on t.unit_2=b.code "+
            " left join t_para_complex_extend d on CONCAT(t.code_t,t.code_s) = d.code_ts " +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.code_ts,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.g_name,#{keywords}) >0 </if>" +
            " order by t.update_time desc,t.code_ts " +
            "</script>")
    IPage<Map<Object,String>> getPageList(Page<Map<Object,String>> pager, @Param(value = "keywords")String keywords, @Param(value = "keywords2")String keywords2);
}
