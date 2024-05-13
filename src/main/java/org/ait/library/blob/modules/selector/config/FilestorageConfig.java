package org.ait.library.blob.modules.selector.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.library.blob.config.GlobalConfig;
import org.ait.library.blob.modules.selector.service.FileStorageService;
import org.ait.library.blob.modules.selector.service.StorageSelectorService;
import org.ait.library.blob.modules.selector.service.impl.FileStorageServiceImpl;
import org.ait.library.blob.modules.selector.service.impl.StorageSelectorServiceImpl;
import org.ait.library.blob.shared.enums.PlatformEnum;
import org.ait.library.blob.shared.serviceskelenton.EngineStorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FilestorageConfig {

  private final GlobalConfig globalConfig;

  private final FilestorageProp filestorageProp;

  private final ApplicationContext context;

  @PostConstruct
  private void validateProperties() {
    log.info("Validate Lumbung Properties");
    PlatformEnum platform = PlatformEnum.getEnum(filestorageProp.getPlatform());
    if (platform == null) {
      log.error(
          ">>>>>>>>>>>>>>>>>>>>>>>>>ERROR: Platform not valid <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
      int exitCode = SpringApplication.exit(context, () -> 0);
      System.exit(exitCode);
    } else {
      log.info(String.format("Storage Platform Selected: %s", platform.name()));
      globalConfig.setPlatform(platform);
    }
  }

  @Bean
  @ConditionalOnClass(FileStorageService.class)
  public FileStorageService fileStorageService(StorageSelectorService storageSelectorService) {
    log.info("FileStorageService class Created");
    return new FileStorageServiceImpl(storageSelectorService);
  }

  @Bean
  @ConditionalOnClass(StorageSelectorService.class)
  public StorageSelectorService storageSelectorService(FilestorageProp filestorageProp,
                                                       List<EngineStorageService> engineStorageServiceList) {
    log.info("StorageSelectorService class Created");
    return new StorageSelectorServiceImpl(filestorageProp, engineStorageServiceList);
  }
}
