package cloud.onion.cms.mapper;


import cloud.onion.cms.model.entity.ArticleInfo;
import cloud.onion.cms.model.res.CountryGuideRes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 允泽
 * @date 2022/7/16
 */
@Mapper
public interface ArticleInfoMapper extends BaseMapper<ArticleInfo> {

    IPage<CountryGuideRes> getCountryGuidePageListByTitle(Page<CountryGuideRes> page, @Param(value = "menuId")Long menuId, @Param(value = "title")String title);
    IPage<CountryGuideRes> getCountryGuidePageList(Page<CountryGuideRes> page, @Param(value = "menuId")Long menuId, @Param(value = "title")String title);
}
