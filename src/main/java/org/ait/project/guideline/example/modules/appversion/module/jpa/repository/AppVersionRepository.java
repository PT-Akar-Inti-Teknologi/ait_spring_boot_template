package org.ait.project.guideline.example.modules.appversion.module.jpa.repository;

import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.ProjectionsVersion;
import org.ait.project.guideline.example.shared.constant.enums.TypeAppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersion, BigInteger>, JpaSpecificationExecutor<AppVersion> {

    Optional<AppVersion> findByVersionAndPlatformIgnoreCase(String version, String platform);

    List<ProjectionsVersion> findVersionByPlatformIgnoreCase(String platform);

    List<AppVersion> findAllByPlatformIgnoreCaseAndType(String platform, TypeAppVersion appVersion);


}
