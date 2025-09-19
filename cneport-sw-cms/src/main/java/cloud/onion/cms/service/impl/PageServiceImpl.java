package cloud.onion.cms.service.impl;


import cloud.onion.cms.mapper.PageMapper;
import cloud.onion.cms.model.convertor.PageConvertor;
import cloud.onion.cms.model.entity.Page;
import cloud.onion.cms.model.res.PageRes;
import cloud.onion.cms.service.IPageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 允泽
 * @date 2022/9/5
 */
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements IPageService {

    @Override
    public PageRes view(String accessPath) throws NotFoundException {
        LambdaQueryWrapper<Page> wrapper = Wrappers.<Page>lambdaQuery();
        wrapper.eq(Page::getAccessPath, accessPath);
        wrapper.orderByDesc(Page::getId);
        wrapper.last("limit 1");
        Page entity = super.getOne(wrapper);
        Optional.ofNullable(entity).orElseThrow(() -> new NotFoundException("404"));
        return PageConvertor.INSTANCE.to(entity);
    }
}
