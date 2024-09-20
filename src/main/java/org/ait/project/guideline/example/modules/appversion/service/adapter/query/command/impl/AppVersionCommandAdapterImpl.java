package org.ait.project.guideline.example.modules.appversion.service.adapter.query.command.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.modules.appversion.exception.AppVersionDuplicateKeyValueException;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.appversion.module.jpa.repository.AppVersionRepository;
import org.ait.project.guideline.example.modules.appversion.service.adapter.query.command.AppVersionCommandAdapter;
import org.ait.project.guideline.example.modules.masterdata.model.specification.AppVersionSpecification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppVersionCommandAdapterImpl implements AppVersionCommandAdapter {

    private final AppVersionRepository appVersionRepository;

    private final AppVersionSpecification appVersionSpecification;

    @Override
    public List<AppVersion> saveAllVersion(List<AppVersion> appVersionList) {
        List<AppVersion> listResponse;
        try {
            listResponse = appVersionRepository.saveAllAndFlush(appVersionList);
        }catch (DataIntegrityViolationException e){
            throw new AppVersionDuplicateKeyValueException();
        }
        return listResponse;
    }

    @Override
    public void deleteAppVersion(List<BigInteger> ids) {
        appVersionRepository.deleteAllById(ids);
    }

    @Override
    public AppVersion update(AppVersion appVersion) {
        return appVersionRepository.save(appVersion);
    }
}