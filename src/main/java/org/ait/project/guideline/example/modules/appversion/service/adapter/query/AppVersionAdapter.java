package org.ait.project.guideline.example.modules.appversion.service.adapter.query;

import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

public interface AppVersionAdapter {

    AppVersion getAppVersion(String version, String platform);

    Page<AppVersion> getPage(Pageable pageable, AppVersionParam appVersionParam);

    List<AppVersion> saveAllVersion(List<AppVersion> appVersionList);

    void deleteAppVersion(List<BigInteger> ids);
}
