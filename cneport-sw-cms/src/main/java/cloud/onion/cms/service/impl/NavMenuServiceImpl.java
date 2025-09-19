package cloud.onion.cms.service.impl;


import cloud.onion.cms.mapper.NavMenuMapper;
import cloud.onion.cms.model.res.MenuRes;
import cloud.onion.cms.service.INavMenuService;
import cloud.onion.core.constant.StatusConst;
import cloud.onion.core.entity.Menu;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NavMenuServiceImpl extends ServiceImpl<NavMenuMapper, Menu> implements INavMenuService {

    @Override
    public List<MenuRes> lists(long parentId, int dynamicArticle) {
        LambdaQueryWrapper<Menu> lambdaQuery = Wrappers.<Menu>lambdaQuery();
        lambdaQuery.ne(Menu::getIsSystem, 1);
        lambdaQuery.eq(Menu::getStatus, StatusConst.NORMAL);
        lambdaQuery.orderByDesc(Menu::getSort);
        lambdaQuery.orderByAsc(Menu::getId);
        if (parentId > 0) {
            lambdaQuery.eq(Menu::getParentId, parentId);
        }
        if (dynamicArticle > 0) {
            lambdaQuery.eq(Menu::getDynamicArticle, dynamicArticle);
        }
        List<Menu> list = super.list(lambdaQuery);
        return list.stream().map(e->{
            MenuRes menuRes = new MenuRes();
            BeanUtils.copyProperties(e, menuRes);
            return menuRes;
        }).collect(Collectors.toList());

    }

    @Override
    public List<MenuRes> getNavTree(long parentId) {
        LambdaQueryWrapper<Menu> lambdaQuery = Wrappers.<Menu>lambdaQuery();
        lambdaQuery.ne(Menu::getIsSystem, 1);
        lambdaQuery.eq(Menu::getStatus,StatusConst.NORMAL);
        lambdaQuery.orderByDesc(Menu::getSort);
        lambdaQuery.orderByAsc(Menu::getId);
        if (parentId > 0) {
            lambdaQuery.eq(Menu::getParentId, parentId);
        }
        List<Menu> list = super.list(lambdaQuery);
        List<MenuRes> vos = list.stream().map(e->{
            MenuRes menuRes = new MenuRes();
            BeanUtils.copyProperties(e, menuRes);
            return menuRes;
        }).collect(Collectors.toList());
        return vos.stream()
                .filter(vo -> vo.getParentId() == 0)
                .peek(vo -> vo.setChildren(getChildren(vo, vos)))
                .collect(Collectors.toList());
    }

    @Override
    public MenuRes view(long id) throws NotFoundException {
        LambdaQueryWrapper<Menu> lambdaQuery = Wrappers.<Menu>lambdaQuery();
        Menu entity = super.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new NotFoundException("404"));
        MenuRes menuRes = new MenuRes();
        BeanUtils.copyProperties(entity, menuRes);
        return menuRes;
    }

    @Override
    public Long[] queryChildrenIds(Long id) {
        List<Menu> categoryEntities = super.getBaseMapper().queryChildren(id);
        Long[] ids = new Long[categoryEntities.size()+1];
        for (int i = 0; i < categoryEntities.size(); i++) {
            Menu entity = categoryEntities.get(i);
            ids[i] = entity.getId();
        }
        ids[categoryEntities.size()] = id;
        return ids;
    }

    @Override
    public Long[] queryParentIds(Long id) {
        List<Menu> categoryEntities = super.getBaseMapper().queryParents(id);
        Long[] ids = new Long[categoryEntities.size()+1];
        for (int i = 0; i < categoryEntities.size(); i++) {
            Menu entity = categoryEntities.get(i);
            ids[i] = entity.getId();
        }
        ids[categoryEntities.size()] = id;
        return ids;
    }

    @Override
    public MenuRes getByPath(String path) throws NotFoundException {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery();
        wrapper.eq(Menu::getPath, path);
        wrapper.last("limit 1");
        Menu navMenu = Optional.ofNullable(super.getOne(wrapper)).orElseThrow(() -> new NotFoundException("404"));
        MenuRes menuRes = new MenuRes();
        BeanUtils.copyProperties(navMenu,menuRes);
        return menuRes;
    }

    @Override
    public List<MenuRes> getParents(Long id) {
        List<Menu> menus = super.getBaseMapper().queryParents(id);
        List<MenuRes> collect = menus.stream().map(e -> {
            MenuRes menuRes = new MenuRes();
            BeanUtils.copyProperties(e, menuRes);
            return menuRes;
        }).collect(Collectors.toList());
        Collections.reverse(collect);
        return collect;
    }

    /**
     * 递归获取下级
     * @param root
     * @param allMenus
     * @return
     */
    private static List<MenuRes> getChildren(MenuRes root, List<MenuRes> allMenus){
        return allMenus.stream()
                .filter(menu -> Objects.equals(menu.getParentId(), root.getId()))
                .peek(menu -> menu.setChildren(getChildren(menu, allMenus)))
                .collect(Collectors.toList());
    }
}
