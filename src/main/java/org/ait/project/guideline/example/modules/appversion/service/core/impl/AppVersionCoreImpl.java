package org.ait.project.guideline.example.modules.appversion.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionDetailResponse;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionTypeResponse;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.appversion.service.adapter.query.AppVersionAdapter;
import org.ait.project.guideline.example.modules.appversion.service.core.AppVersionCore;
import org.ait.project.guideline.example.modules.appversion.transform.AppVersionTransform;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppVersionCoreImpl implements AppVersionCore {

    private final ResponseHelper responseHelper;

    private final AppVersionAdapter appVersionAdapter;

    private final AppVersionTransform appVersionTransform;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionTypeResponse>>> getAppVersion(String version, String platform) {
        AppVersion appVersion = appVersionAdapter.getAppVersion(version, platform);

        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, appVersionTransform.createResponseAppVersion(appVersion));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> getAllAppVersion(Pageable pageable, AppVersionParam appVersionParam) {
        Page<AppVersionDetailResponse> appVersionRequests = appVersionAdapter.getPage(pageable, appVersionParam)
                .map(appVersionTransform::createResponse);
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, appVersionRequests, appVersionRequests.getContent());
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> saveAppVersions(List<AppVersionDetailResponse> appVersionRequests) {
        List<AppVersion> appVersionList = appVersionAdapter.saveAllVersion(appVersionTransform.mapListRequestToAppVersion(appVersionRequests));
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, null, appVersionTransform.mapListAppVersionToResponse(appVersionList));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<Boolean>>> deleteAppVersion(List<BigInteger> ids) {
            appVersionAdapter.deleteAppVersion(ids);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, true);
    }
}
