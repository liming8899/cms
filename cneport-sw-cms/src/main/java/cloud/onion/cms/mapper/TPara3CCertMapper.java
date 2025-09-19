package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TPara3CCert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;
@Mapper
public interface TPara3CCertMapper extends BaseMapper<TPara3CCert> {

    @Select(value = "<script>" +
            " select t.* from  `t_para_3c_cert` t " +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.code_t_s,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.g_name,#{keywords}) >0 </if>" +
            " order by t.insert_time desc,t.code_t_s desc" +
            "</script>")
    IPage<TPara3CCert> getPageList(Page<TPara3CCert> pager, @Param(value = "keywords")String keywords, @Param(value = "keywords2")String keywords2);
}
