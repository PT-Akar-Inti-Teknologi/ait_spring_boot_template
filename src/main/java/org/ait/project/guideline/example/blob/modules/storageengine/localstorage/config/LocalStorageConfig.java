package org.ait.project.guideline.example.blob.modules.storageengine.localstorage.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.blob.config.GlobalConfig;
import org.ait.project.guideline.example.blob.modules.storageengine.localstorage.service.StorageService;
import org.ait.project.guideline.example.blob.shared.enums.PlatformEnum;
import org.ait.project.guideline.example.blob.shared.serviceskelenton.EngineStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LocalStorageConfig {

  private final GlobalConfig config;

  @Bean
  @ConditionalOnProperty(prefix = "filestorage", name = "platform", havingValue = "local")
  public EngineStorageService localStorageService(LocalStorageProp localStorageProp) {
    if (config.getPlatform() == PlatformEnum.LOCAL) {
      log.info("LocalStorageService Created");
      return new StorageService(localStorageProp);
    }
    return new StorageService(null);
  }
}
