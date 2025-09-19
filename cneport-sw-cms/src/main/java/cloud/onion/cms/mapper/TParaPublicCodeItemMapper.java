package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TParaComplex;
import cloud.onion.cms.model.entity.TParaPublicCodeItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TParaPublicCodeItemMapper extends BaseMapper<TParaPublicCodeItem> {

    @Select(value = "<script>" +
            "SELECT t.* FROM t_para_public_code_item t INNER JOIN t_para_public_code_catalog g ON t.catalog_oid = g.oid AND g.CODE =#{publicCode}\n" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"2\"'> where instr(t.code,#{keywords}) >0 </if>" +
            "<if test='keywords2 != null and keywords2.trim() != \"\" and keywords2==\"1\"'> where instr(t.name,#{keywords}) >0 </if>" +
            "order by t.order_no desc,t.code" +
            "</script>")
    IPage<TParaPublicCodeItem> getPageList(Page<TParaPublicCodeItem> pager,@Param(value = "publicCode") String publicCode,  @Param(value = "keywords")String keywords, @Param(value = "keywords2")String keywords2);

    @Select(value = "<script>" +
            "SELECT t.* FROM t_para_public_code_item t INNER JOIN t_para_public_code_catalog g ON t.catalog_oid = g.oid AND g.CODE =#{publicCode}\n" +
            "order by t.order_no desc,t.code" +
            "</script>")
    List<TParaPublicCodeItem> list(@Param(value = "publicCode") String publicCode);
}
