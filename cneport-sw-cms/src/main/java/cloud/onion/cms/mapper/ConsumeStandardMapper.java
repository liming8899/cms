package cloud.onion.cms.mapper;


import cloud.onion.cms.model.entity.ConsumeStandard;
import cloud.onion.cms.model.entity.TParaExchrate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ConsumeStandardMapper extends BaseMapper<ConsumeStandard> {
    /**
     * 获取 单耗标准列表信息
     *
     * @param pager     当前页数
     * @param keywords  查询关键字
     * @param keywords2 查询关键字
     * @return IPage<TParaExchrate>
     */
    @Select(value = "<script>" +
            " SELECT t.* FROM `consume_standard` t " +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.ex_code_ts,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.ex_g_name,#{keywords}) >0 </if>" +
            " order by t.create_date" +
            "</script>")
    IPage<ConsumeStandard> getPageList(Page<TParaExchrate> pager, @Param("keywords") String keywords, @Param("keywords2") String keywords2);

}
