package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TParaMerchElement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TParaMerchElementMapper extends BaseMapper<TParaMerchElement> {
    @Select(value = "<script>" +
            " SELECT t.* FROM `t_para_merch_element` t " +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.code_t_s,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.g_name,#{keywords}) >0 </if>" +
            " order by t.code_t_s,t.s_num  " +
            "</script>")
    IPage<TParaMerchElement> getPageList(Page<TParaMerchElement> pager, @Param(value = "keywords")String keywords, @Param(value = "keywords2")String keywords2);
}
