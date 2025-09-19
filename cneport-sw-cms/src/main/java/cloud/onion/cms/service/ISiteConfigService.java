package cloud.onion.cms.service;


import cloud.onion.cms.model.entity.SiteConfig;
import cloud.onion.cms.model.res.SiteConfigRes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 允泽
 * @date 2022/7/15
 */
public interface ISiteConfigService extends IService<SiteConfig> {

    /**
     * 获取站点配置信息
     * @return
     */
    SiteConfigRes getSiteConfig();
}
