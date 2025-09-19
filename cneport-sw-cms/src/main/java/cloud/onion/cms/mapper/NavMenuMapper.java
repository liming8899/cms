package cloud.onion.cms.mapper;


import cloud.onion.core.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NavMenuMapper extends BaseMapper<Menu> {


    @Select("SELECT t3.* FROM(SELECT t1.*, IF (FIND_IN_SET(parent_id,#{id}) > 0, CONCAT(#{id}, ',', id),'0') AS is_child FROM(SELECT t.* FROM bm_nav_menu AS t where t.is_system <> 1 ORDER BY t.id ASC) t1) t3 WHERE is_child != '0'")
    List<Menu> queryChildren(@Param("id") Long id);

    @Select("select t3.* from(select t1.*,if (FIND_IN_SET(id,#{id}) > 0,CONCAT( parent_id,',',#{id}),'0') as is_parent from (select t.* from bm_nav_menu as t where t.is_system <> 1 order by t.id desc) t1) t3 where t3.is_parent != '0'")
    List<Menu> queryParents(@Param("id") Long id);
}
