package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.BannerMapper;
import cloud.onion.cms.model.convertor.BannerConvertor;
import cloud.onion.cms.model.entity.Banner;
import cloud.onion.cms.model.res.BannerRes;
import cloud.onion.cms.service.IBannerService;
import cloud.onion.core.constant.StatusConst;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 允泽
 * @date 2022/9/15
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Override
    public List<BannerRes> lists(Integer position) {
        LambdaQueryWrapper<Banner> wrapper = Wrappers.<Banner>lambdaQuery();
        wrapper.eq(Banner::getStatus, StatusConst.NORMAL);
        wrapper.eq(Banner::getPosition, position);
        wrapper.orderByAsc(Banner::getSort);
        List<Banner> entities = super.list(wrapper);
        return entities.stream().map(BannerConvertor.INSTANCE::to).collect(Collectors.toList());
    }

    @Override
    public BannerRes one(Integer position) {
        LambdaQueryWrapper<Banner> wrapper = Wrappers.<Banner>lambdaQuery();
        wrapper.eq(Banner::getStatus, StatusConst.NORMAL);
        wrapper.eq(Banner::getPosition, position);
        wrapper.orderByDesc(Banner::getId);
        wrapper.last("limit 1");
        Banner banner = Optional.ofNullable(super.getOne(wrapper)).orElse(new Banner());
        return BannerConvertor.INSTANCE.to(banner);
    }
}
