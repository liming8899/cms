package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TParaComplexBan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TParaComplexBanMapper extends BaseMapper<TParaComplexBan> {
    @Select(value = "<script>" +
            "select t.* from ( SELECT tt.code,tt.name,case tt.banlist when 'I' then '进口' when 'E' then '出口' else '进口，出口' end as banlist FROM `t_para_complex_ban` tt) t" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.code,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.name,#{keywords}) >0 </if>" +
            " order by t.code desc" +
            "</script>")
    IPage<TParaComplexBan> getPageList(Page<TParaComplexBan> pager, @Param(value = "keywords")String keywords, @Param(value = "keywords2")String keywords2);
}
