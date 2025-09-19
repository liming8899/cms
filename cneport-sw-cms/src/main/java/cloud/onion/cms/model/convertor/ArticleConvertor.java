package cloud.onion.cms.model.convertor;

import cloud.onion.cms.model.entity.ArticleInfo;
import cloud.onion.cms.model.entity.XhInfoContent;
import cloud.onion.cms.model.res.ArticleRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author 允泽
 * @date 2022/8/1
 */
@Mapper
public interface ArticleConvertor {
    ArticleConvertor INSTANCE = Mappers.getMapper(ArticleConvertor.class);

    @Mappings({
            @Mapping(target = "id", source = "article.id"),
            @Mapping(target = "title", source = "article.title"),
            @Mapping(target = "enTitle", source = "article.enTitle"),
            @Mapping(target = "ruTitle", source = "article.ruTitle"),
            @Mapping(target = "coverImage", source = "article.coverImage"),
            @Mapping(target = "description", source = "article.description"),
            @Mapping(target = "enDescription", source = "article.enDescription"),
            @Mapping(target = "ruDescription", source = "article.ruDescription"),
            @Mapping(target = "content", source = "article.content"),
            @Mapping(target = "enContent", source = "article.enContent"),
            @Mapping(target = "ruContent", source = "article.ruContent"),
            @Mapping(target = "author", source = "article.author"),
            @Mapping(target = "source", source = "article.source"),
            @Mapping(target = "ruSource", source = "article.ruSource"),
            @Mapping(target = "enSource", source = "article.enSource"),
            @Mapping(target = "hits", source = "article.hits"),
            @Mapping(target = "createTime", source = "article.createTime")
    })
    ArticleRes fromArticle(ArticleInfo article);


    @Mappings({
            @Mapping(target = "id", source = "content.id"),
            @Mapping(target = "title", source = "content.title"),
            @Mapping(target = "coverImage", source = "content.logoFile"),
            @Mapping(target = "description", source = "content.summary"),
            @Mapping(target = "content", source = "content.content"),
            @Mapping(target = "author", source = "content.author"),
            @Mapping(target = "source", source = "content.externalSource"),
            @Mapping(target = "createTime", source = "content.publishTime")
    })
    ArticleRes fromContent(XhInfoContent content);
}
