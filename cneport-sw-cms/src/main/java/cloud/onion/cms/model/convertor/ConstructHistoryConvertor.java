package cloud.onion.cms.model.convertor;


import cloud.onion.cms.model.entity.ArticleInfo;
import cloud.onion.cms.model.entity.ConstructHistoryInfo;
import cloud.onion.cms.model.res.ArticleRes;
import cloud.onion.cms.model.res.ConstructHistoryRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConstructHistoryConvertor{
    ConstructHistoryConvertor INSTANCE = Mappers.getMapper(ConstructHistoryConvertor.class);

    @Mappings({
            @Mapping(target = "id", source = "historyInfo.id"),
            @Mapping(target = "title", source = "historyInfo.title"),
            @Mapping(target = "enTitle", source = "historyInfo.enTitle"),
            @Mapping(target = "ruTitle", source = "historyInfo.ruTitle"),
            @Mapping(target = "coverImage", source = "historyInfo.coverImage"),
            @Mapping(target = "description", source = "historyInfo.description"),
            @Mapping(target = "enDescription", source = "historyInfo.enDescription"),
            @Mapping(target = "ruDescription", source = "historyInfo.ruDescription"),
            @Mapping(target = "historyTime", source = "historyInfo.historyTime"),
            @Mapping(target = "historyTimeStr", source = "historyInfo.historyTimeStr"),
            @Mapping(target = "createTime", source = "historyInfo.createTime"),
            @Mapping(target = "status", source = "historyInfo.status"),
    })
    ConstructHistoryRes fromHistory(ConstructHistoryInfo historyInfo);

}
