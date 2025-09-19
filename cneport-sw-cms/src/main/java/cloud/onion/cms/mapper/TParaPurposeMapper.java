package cloud.onion.cms.mapper;


import cloud.onion.cms.model.entity.TParaPurpose;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TParaPurposeMapper extends BaseMapper<TParaPurpose> {
    @Select(value = "<script>" +
            " SELECT t.* FROM `t_para_purpose` t " +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.code_t_s,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.g_name,#{keywords}) >0 </if>" +
            "</script>")
    IPage<TParaPurpose> getPageList(Page<TParaPurpose> pager, @Param("keywords") String keywords, @Param("keywords2") String keywords2);
}
