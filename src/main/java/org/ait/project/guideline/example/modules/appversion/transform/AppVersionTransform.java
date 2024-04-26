package org.ait.project.guideline.example.modules.appversion.transform;

import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionDetailResponse;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionTypeResponse;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppVersionTransform {

    @Named("createResponseAppVersion")
    AppVersionTypeResponse createResponseAppVersion(AppVersion appVersion);


    @Named("createResponse")
    AppVersionDetailResponse createResponse(AppVersion appVersion);

    @IterableMapping(qualifiedByName = "mapRequestToAppVersion")
    List<AppVersion> mapListRequestToAppVersion(List<AppVersionDetailResponse> appVersionRespons);

    @Named("mapRequestToAppVersion")
    AppVersion mapRequestToAppVersion(AppVersionDetailResponse appVersionRequest);

    @IterableMapping(qualifiedByName = "createResponse")
    List<AppVersionDetailResponse> mapListAppVersionToResponse(List<AppVersion> appVersionList);



}
