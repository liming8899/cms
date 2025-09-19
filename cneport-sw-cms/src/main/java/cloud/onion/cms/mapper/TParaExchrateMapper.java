package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.TParaExchrate;
import cloud.onion.cms.model.entity.TParaMerchElement;
import cloud.onion.cms.model.entity.vo.TParaExchrateVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper
public interface TParaExchrateMapper extends BaseMapper<TParaExchrate> {

    /**
     * 获取 外汇汇率列表信息
     * @param pager 当前页数
     * @param keywords 查询关键字
     * @param keywords2 查询关键字
     * @return IPage<TParaExchrate>
     */
    @Select(value = "<script>" +
            " SELECT t.*,CONCAT(a.name,'(',t.curr_code,')') AS currCode,date_format(end_date,'%Y-%m') AS years FROM t_para_exchrate AS t" +
            " left join ( SELECT i.* from t_para_public_code_catalog c inner join t_para_public_code_item i  on c.oid=i.catalog_oid  where c.code='CURR_COMBINATION')a on t.curr_code=a.code AND t.curr_code IS NOT NULL " +
            "<if test='keywords2 != null and keywords2.trim() != \"\"'> where t.curr_code= #{keywords2} </if>" +
            " ORDER BY last_modify_date DESC " +
            "</script>")
    IPage<TParaExchrate> getPageList(Page<TParaExchrate> pager, @Param(value = "keywords") String keywords, @Param(value = "keywords2") String keywords2);

    /**
     * 获取币制种类
     *
     * @return List<String>
     */
    @Select(value = "<script>"+
            "SELECT CONCAT(i.code,'(',i.name,')')AS name,i.`code` from t_para_public_code_catalog c inner join t_para_public_code_item i  on c.oid=i.catalog_oid  where c.code='CURR_COMBINATION'" +
            "</script>")
    List<TParaExchrateVo> getExchangeType();
}
