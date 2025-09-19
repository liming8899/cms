package cloud.onion.cms.model.convertor;

import cloud.onion.cms.model.entity.Banner;
import cloud.onion.cms.model.res.BannerRes;
import cloud.onion.core.base.BaseConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 允泽
 * @date 2022/8/1
 */
@Mapper
public interface BannerConvertor extends BaseConvertor<Banner, Banner, BannerRes> {

    BannerConvertor INSTANCE = Mappers.getMapper(BannerConvertor.class);

}
