package cloud.onion.cms.service;


import cloud.onion.cms.model.res.MenuRes;
import cloud.onion.core.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;

public interface INavMenuService extends IService<Menu> {

    /**
     * 获取菜单
     * @param parentId
     * @return
     */
    List<MenuRes> lists(long parentId, int dynamicArticle);

    /**
     * 获取导航菜单
     * @param parentId
     * @return
     */
    List<MenuRes> getNavTree(long parentId);

    /**
     * 根据菜单ID获取菜单信息
     * @param id
     * @return
     */
    MenuRes view(long id) throws NotFoundException;


    /**
     * 获取所有子类的id数组
     * @param id
     * @return
     */
    Long[] queryChildrenIds(Long id);

    /**
     * 获取所有父类的id数组
     * @param id
     * @return
     */
    Long[] queryParentIds(Long id);

    MenuRes getByPath(String path) throws NotFoundException;

    List<MenuRes> getParents(Long id);
}
