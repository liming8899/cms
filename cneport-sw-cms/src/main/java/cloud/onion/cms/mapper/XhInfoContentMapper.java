package cloud.onion.cms.mapper;


import cloud.onion.cms.model.entity.XhInfoContent;
import cloud.onion.cms.model.res.ArticleRes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 允泽
 * @date 2022/8/1
 */
@Mapper
public interface XhInfoContentMapper extends BaseMapper<XhInfoContent> {

    IPage<ArticleRes> getPageList(Page<ArticleRes> page,
                                  @Param(value = "menuIds")String[] menuIds);
}
