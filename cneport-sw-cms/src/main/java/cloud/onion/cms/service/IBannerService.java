package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.Banner;
import cloud.onion.cms.model.res.BannerRes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 允泽
 * @date 2022/9/15
 */
public interface IBannerService extends IService<Banner> {

    List<BannerRes> lists(Integer position);

    BannerRes one(Integer position);
}
