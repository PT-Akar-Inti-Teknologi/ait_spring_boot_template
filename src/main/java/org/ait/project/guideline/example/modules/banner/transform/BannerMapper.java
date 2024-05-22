package org.ait.project.guideline.example.modules.banner.transform;

import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    BannerRes convertToRes(Banner banner);

    @Mapping(target = "title", source = "param.title")
    @Mapping(target = "description", source = "param.description")
    @Mapping(target = "imageFile", source = "imageFile")
    @Mapping(target = "thumbnailFile", source = "thumbnailFile")
    Banner convertToEntity(BannerParam param, String imageFile, String thumbnailFile);

    @Mapping(target = "title", source = "param.title")
    @Mapping(target = "description", source = "param.description")
    @Mapping(target = "imageFile", source = "imageFile")
    @Mapping(target = "thumbnailFile", source = "thumbnailFile")
    Banner update(@MappingTarget Banner banner, BannerParam param, String imageFile, String thumbnailFile);

}