package org.ait.project.guideline.example.blob.modules.selector.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.blob.modules.selector.config.FilestorageProp;
import org.ait.project.guideline.example.blob.modules.selector.service.StorageSelectorService;
import org.ait.project.guideline.example.blob.shared.enums.PlatformEnum;
import org.ait.project.guideline.example.blob.shared.exception.PlatformNotFoundException;
import org.ait.project.guideline.example.blob.shared.serviceskelenton.EngineStorageService;

@RequiredArgsConstructor
public class StorageSelectorServiceImpl implements StorageSelectorService {

  private final FilestorageProp filestorageProp;
  private final List<EngineStorageService> engineStorageServiceList;

  @Override
  public EngineStorageService getEngineStorageService() {
    return engineStorageServiceList.stream()
        .filter(engineStorageService -> engineStorageService.platformIs(
            PlatformEnum.getEnum(filestorageProp.getPlatform())))
        .findFirst().orElseThrow(PlatformNotFoundException::new);
  }
}
