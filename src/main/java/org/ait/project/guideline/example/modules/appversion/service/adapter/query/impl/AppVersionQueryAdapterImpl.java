package org.ait.project.guideline.example.modules.appversion.service.adapter.query.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.modules.appversion.exception.AppVersionDuplicateKeyValueException;
import org.ait.project.guideline.example.modules.appversion.exception.AppVersionNotFoundException;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.ProjectionsVersion;
import org.ait.project.guideline.example.modules.appversion.module.jpa.repository.AppVersionRepository;
import org.ait.project.guideline.example.modules.appversion.service.adapter.query.AppVersionQueryAdapter;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.modules.masterdata.model.specification.AppVersionSpecification;
import org.ait.project.guideline.example.shared.constant.enums.TypeAppVersion;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ModuleDescriptor.Version;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppVersionQueryAdapterImpl implements AppVersionQueryAdapter {

    private final AppVersionRepository appVersionRepository;

    private final AppVersionSpecification appVersionSpecification;

    @Override
    public AppVersion getAppVersion(String version, String platform) {
        return appVersionRepository.findByVersionAndPlatformIgnoreCase(version, platform).orElse(new AppVersion());
    }



    @Override
    public Page<AppVersion> getPage(Pageable pageable, AppVersionParam appVersionParam) {
        return appVersionRepository.findAll(appVersionSpecification.predicate(appVersionParam),
                appVersionSpecification.defaultPageSort(pageable));
    }

    @Override
    public AppVersion getLastVersion(String platform) {
        List<ProjectionsVersion> versions = appVersionRepository.findVersionByPlatformIgnoreCase(platform);
        if(versions.isEmpty()){
         throw new AppVersionNotFoundException();
        }
        List<Version> sortedVersion = versions.stream()
                .map(ProjectionsVersion::getVersion)
                .map(Version::parse)
                .sorted().toList();

        String stringVersion = sortedVersion.get(sortedVersion.size()-1).toString();
        AppVersion lastVersionApp = new AppVersion();
        lastVersionApp.setVersion(stringVersion);

        for(ProjectionsVersion version : versions){
            if(version.getVersion().equals(stringVersion)){
                lastVersionApp.setType(TypeAppVersion.valueOf(version.getType()));
            }
        }
        return lastVersionApp;
    }

    @Override
    public List<AppVersion> getAllByPlatformAndType(String platform, TypeAppVersion typeAppVersion) {
        return appVersionRepository.findAllByPlatformIgnoreCaseAndType(platform, typeAppVersion);
    }

    @Override
    public Optional<AppVersion> findById(BigInteger id) {
        return appVersionRepository.findById(id);
    }
}
