package org.ait.project.guideline.example.modules.appversion.service.adapter.query.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.appversion.exception.AppVersionNotFoundException;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.appversion.module.jpa.repository.AppVersionRepository;
import org.ait.project.guideline.example.modules.appversion.service.adapter.query.AppVersionAdapter;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.modules.masterdata.model.specification.AppVersionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppVersionAdapterImpl implements AppVersionAdapter {

    private final AppVersionRepository appVersionRepository;

    private final AppVersionSpecification appVersionSpecification;

    @Override
    public AppVersion getAppVersion(String version, String platform) {
        return appVersionRepository.findByVersionAndPlatform(version, platform).orElseThrow(AppVersionNotFoundException::new);
    }

    @Override
    public Page<AppVersion> getPage(Pageable pageable, AppVersionParam appVersionParam) {
        return appVersionRepository.findAll(appVersionSpecification.predicate(appVersionParam),
                appVersionSpecification.buildPageRequest(pageable));
    }

    @Override
    public List<AppVersion> saveAllVersion(List<AppVersion> appVersionList) {
        return appVersionRepository.saveAllAndFlush(appVersionList);
    }

    @Override
    public void deleteAppVersion(List<BigInteger> ids) {
        appVersionRepository.deleteAllById(ids);
    }
}
