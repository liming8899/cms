package cloud.onion.cms.service.impl;


import cloud.onion.cms.mapper.SiteConfigMapper;
import cloud.onion.cms.model.entity.SiteConfig;
import cloud.onion.cms.model.res.SiteConfigRes;
import cloud.onion.cms.service.ISiteConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author 允泽
 * @date 2022/7/15
 */
@Slf4j
@Service
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfig> implements ISiteConfigService {

    @Override
    public SiteConfigRes getSiteConfig() {
        SiteConfigRes vo = new SiteConfigRes();
        LambdaQueryWrapper<SiteConfig> wrapper = Wrappers.<SiteConfig>lambdaQuery();
        wrapper.orderByDesc(SiteConfig::getId);
        wrapper.last("limit 1");
        SiteConfig entity = super.getOne(wrapper);
        if (entity != null) {
            BeanUtils.copyProperties(entity, vo);
        }
        return vo;
    }
}
