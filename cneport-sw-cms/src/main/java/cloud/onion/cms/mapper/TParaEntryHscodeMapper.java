package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TParaEntryHscode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Li
 */
@Mapper
public interface TParaEntryHscodeMapper extends BaseMapper<TParaEntryHscode> {
    @Select(value = "<script>" +
            " SELECT t.* FROM `t_para_entry_hscode` t where g_name is not null   " +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> and instr(t.code_t_s,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> and instr(t.g_name,#{keywords}) >0 </if>" +
            " </script>")
    IPage<TParaEntryHscode> getPageList(Page<TParaEntryHscode> pager, @Param("keywords") String keywords, @Param("keywords2") String keywords2);
}
