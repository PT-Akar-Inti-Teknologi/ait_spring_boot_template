package org.ait.project.guideline.example.modules.appversion.service.adapter.query.command;

import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;

import java.math.BigInteger;
import java.util.List;

public interface AppVersionCommandAdapter {

    List<AppVersion> saveAllVersion(List<AppVersion> appVersionList);

    void deleteAppVersion(List<BigInteger> ids);

    AppVersion update(AppVersion appVersion);
}
