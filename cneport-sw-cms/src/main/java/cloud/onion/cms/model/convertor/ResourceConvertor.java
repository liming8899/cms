package cloud.onion.cms.model.convertor;

import cloud.onion.cms.model.entity.XhInfoResource;
import cloud.onion.cms.model.res.FileRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author 允泽
 * @date 2022/8/1
 */
@Mapper
public interface ResourceConvertor {

    ResourceConvertor INSTANCE = Mappers.getMapper(ResourceConvertor.class);

    @Mappings({
            @Mapping(target = "name", source = "resource.originalName"),
            @Mapping(target = "url", source = "resource.attachUrl"),
    })
    FileRes fromResource(XhInfoResource resource);
}
