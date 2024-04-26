package org.ait.project.guideline.example.modules.appversion.module.jpa.repository;

import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersion, BigInteger>, JpaSpecificationExecutor<AppVersion> {

    Optional<AppVersion> findByVersionAndPlatform(String version, String platform);


}
