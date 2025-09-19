package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TParaComplexChange;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface TParaComplexChangeMapper extends BaseMapper<TParaComplexChange> {
    @Select(value = "<script>" +
            " select " +
            "date_format(t.operate_date, '%Y-%m-%d') as operate_date_new," +
            "t.code_t_s,t.g_name,t.low_rate ,t.control_ma ,t.g_name_old ,t.low_rate_old ,control_ma_old ," +
            "CONCAT(a.name, '(', t.unit_1, ')') as unit_name_1," +
            "ifnull(CONCAT(b.name, '(', t.unit_2, ')'), '') as unit_name_2," +
            "CONCAT(m.name, '(', t.unit_1_old, ')') as unit_1_old_name," +
            "ifnull(CONCAT(n.name, '(', t.unit_2_old, ')'), '') as unit_2_old_name," +
            "(case t.modify_mark when '1' then '已变更' when '2' then '已删除' when '3' then '新增' end) as modify_mark," +
            "(case t.g_name_is_modify when 'Y' then '已变更' when 'N' then '未变更' end )as g_name_is_modify," +
            "(case t.low_rate_is_modify when 'Y' then '已变更' when 'N' then '未变更' end )as low_rate_is_modify," +
            "(case t.unit_1_is_modify when 'Y' then '已变更' when 'N' then '未变更' end )as unit_1_is_modify," +
            "(case t.unit_2_is_modify when 'Y' then '已变更' when 'N' then '未变更' end )as unit_2_is_modify," +
            "(case t.control_ma_is_modify when 'Y' then '已变更' when 'N' then '未变更' end )as control_ma_is_modify" +
            " from `t_para_complex_change` t " +
            " left join ( SELECT i.* from t_para_public_code_catalog c inner join t_para_public_code_item i  on c.oid=i.catalog_oid  where c.code='UNIT')a on t.unit_1=a.code   "+
            " left join ( SELECT i.* from t_para_public_code_catalog c inner join t_para_public_code_item i  on c.oid=i.catalog_oid  where c.code='UNIT')b on t.unit_2=b.code "+
            " left join ( SELECT i.* from t_para_public_code_catalog c inner join t_para_public_code_item i  on c.oid=i.catalog_oid  where c.code='UNIT')m on t.unit_1_old=m.code "+
            " left join ( SELECT i.* from t_para_public_code_catalog c inner join t_para_public_code_item i  on c.oid=i.catalog_oid  where c.code='UNIT')n on t.unit_2_old=n.code "+
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.code_t_s,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.g_name,#{keywords}) >0 </if>" +
            " order by t.operate_date desc,t.code_t_s " +
            "</script>")
    IPage<Map<Object,String>> getPageList(Page<Map<Object,String>> pager, @Param(value = "keywords")String keywords, @Param(value = "keywords2")String keywords2);
}
