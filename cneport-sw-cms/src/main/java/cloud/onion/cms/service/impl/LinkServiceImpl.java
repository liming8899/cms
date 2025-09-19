package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.LinkMapper;
import cloud.onion.cms.model.entity.Link;
import cloud.onion.cms.model.res.LinkRes;
import cloud.onion.cms.service.ILinkService;
import cloud.onion.core.constant.StatusConst;
import cloud.onion.core.utils.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 允泽
 * @date 2022/7/15
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {


    @Override
    public List<LinkRes> getLists() {
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, StatusConst.NORMAL);
        lambdaQueryWrapper.orderByDesc(Link::getSort).orderByDesc(Link::getId);
        List<Link> list = super.list(lambdaQueryWrapper);
        List<LinkRes> vos = list.stream().map(e -> {
            LinkRes linkRes = new LinkRes();
            BeanUtils.copyProperties(e, linkRes);
            return linkRes;
        }).collect(Collectors.toList());
        return TreeUtil.build(vos);
    }
}
