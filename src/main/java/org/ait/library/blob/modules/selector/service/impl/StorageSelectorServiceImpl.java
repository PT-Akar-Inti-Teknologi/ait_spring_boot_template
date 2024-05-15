package org.ait.library.blob.modules.selector.service.impl;

import lombok.RequiredArgsConstructor;
import org.ait.library.blob.modules.selector.config.FilestorageProp;
import org.ait.library.blob.modules.selector.service.StorageSelectorService;
import org.ait.library.blob.shared.enums.PlatformEnum;
import org.ait.library.blob.shared.exception.PlatformNotFoundException;
import org.ait.library.blob.shared.serviceskelenton.EngineStorageService;

import java.util.List;

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
