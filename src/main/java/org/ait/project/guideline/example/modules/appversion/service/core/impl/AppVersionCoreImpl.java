package org.ait.project.guideline.example.modules.appversion.service.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionDetailResponse;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionTypeResponse;
import org.ait.project.guideline.example.modules.appversion.exception.AppVersionNotFoundException;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.appversion.service.adapter.query.AppVersionQueryAdapter;
import org.ait.project.guideline.example.modules.appversion.service.core.AppVersionCore;
import org.ait.project.guideline.example.modules.appversion.transform.AppVersionTransform;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppVersionCoreImpl implements AppVersionCore {

    private final ResponseHelper responseHelper;

    private final AppVersionQueryAdapter appVersionQueryAdapter;

    private final AppVersionTransform appVersionTransform;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionTypeResponse>>> getAppVersion(String version, String platform) {
        AppVersion appVersion = new AppVersion();
        AppVersion appVersionCondition = appVersionQueryAdapter.getLastVersion(platform);
        DefaultArtifactVersion currentVersion = new DefaultArtifactVersion(version);
        DefaultArtifactVersion lastVersion = new DefaultArtifactVersion(appVersionCondition.getVersion());
        if(currentVersion.compareTo(lastVersion) < 0){
            appVersion.setType(appVersionCondition.getType());
        }else{
            throw new AppVersionNotFoundException();
        }


        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, appVersionTransform.createResponseAppVersion(appVersion));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionDetailResponse>>> getDetail(BigInteger id) {
        Optional<AppVersion> appVersion = appVersionQueryAdapter.findById(id);
        if (appVersion.isEmpty()) {
            throw new AppVersionNotFoundException();
        }

        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, appVersionTransform.createResponse(appVersion.get()));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> getAllAppVersion(Pageable pageable, AppVersionParam appVersionParam) {
        Page<AppVersionDetailResponse> appVersionRequests = appVersionQueryAdapter.getPage(pageable, appVersionParam)
                .map(appVersionTransform::createResponse);
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, appVersionRequests, appVersionRequests.getContent());
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> saveAppVersions(List<AppVersionDetailResponse> appVersionRequests) {
        List<AppVersion> appVersionList = appVersionQueryAdapter.saveAllVersion(appVersionTransform.mapListRequestToAppVersion(appVersionRequests));
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, null, appVersionTransform.mapListAppVersionToResponse(appVersionList));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<Boolean>>> deleteAppVersion(List<BigInteger> ids) {
            appVersionQueryAdapter.deleteAppVersion(ids);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, true);
    }
}
