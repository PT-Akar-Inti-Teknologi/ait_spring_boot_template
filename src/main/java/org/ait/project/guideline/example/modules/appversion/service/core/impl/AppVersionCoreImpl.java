package org.ait.project.guideline.example.modules.appversion.service.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionDetailResponse;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionTypeResponse;
import org.ait.project.guideline.example.modules.appversion.exception.AppVersionNotFoundException;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.appversion.service.adapter.query.AppVersionAdapter;
import org.ait.project.guideline.example.modules.appversion.service.core.AppVersionCore;
import org.ait.project.guideline.example.modules.appversion.transform.AppVersionTransform;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.constant.enums.TypeAppVersion;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppVersionCoreImpl implements AppVersionCore {

    private final ResponseHelper responseHelper;

    private final AppVersionAdapter appVersionAdapter;

    private final AppVersionTransform appVersionTransform;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionTypeResponse>>> getAppVersion(String version, String platform) {
        AppVersion appVersion = appVersionAdapter.getAppVersion(version, platform);

        if(appVersion.getVersion() == null){
            AppVersion appVersionCondition = appVersionAdapter.getLastVersion(platform);
            DefaultArtifactVersion currentVersion = new DefaultArtifactVersion(version);
            DefaultArtifactVersion lastVersion = new DefaultArtifactVersion(appVersionCondition.getVersion());
            if(currentVersion.compareTo(lastVersion) < 0){
                appVersion.setType(appVersionCondition.getType());
            }else{
                throw new AppVersionNotFoundException();
            }
        }

        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, appVersionTransform.createResponseAppVersion(appVersion));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> getAllAppVersion(Pageable pageable, AppVersionParam appVersionParam) {
        Page<AppVersionDetailResponse> appVersionRequests = appVersionAdapter.getPage(pageable, appVersionParam)
                .map(appVersionTransform::createResponse);
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, appVersionRequests, appVersionRequests.getContent());
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> saveAppVersions(List<AppVersionDetailResponse> appVersionRequests) {
        List<AppVersion> appVersionList = appVersionAdapter.saveAllVersion(appVersionTransform.mapListRequestToAppVersion(appVersionRequests));

        if(appVersionRequests.size() == 1 && appVersionRequests.get(0).getType().equals(TypeAppVersion.FORCE_UPDATE)){
            List<AppVersion> versionList = appVersionAdapter.getAllByPlatformAndType(appVersionRequests.get(0).getPlatform(), TypeAppVersion.SOFT_UPDATE);
            DefaultArtifactVersion currentVersion = new DefaultArtifactVersion(appVersionRequests.get(0).getVersion());
            List<AppVersion> listToUpdate = new ArrayList<>();
            for(AppVersion version : versionList){
                DefaultArtifactVersion dataVersion = new DefaultArtifactVersion(version.getVersion());
                if(dataVersion.compareTo(currentVersion) < 0){
                    log.info("id : "+ version.getId() + " and version : "+ version.getVersion() );
                    version.setType(TypeAppVersion.FORCE_UPDATE);
                    listToUpdate.add(version);
                }
            }

            if(!listToUpdate.isEmpty()){
                appVersionAdapter.saveAllVersion(listToUpdate);
            }
        }

        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, null, appVersionTransform.mapListAppVersionToResponse(appVersionList));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<Boolean>>> deleteAppVersion(List<BigInteger> ids) {
            appVersionAdapter.deleteAppVersion(ids);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, true);
    }
}
