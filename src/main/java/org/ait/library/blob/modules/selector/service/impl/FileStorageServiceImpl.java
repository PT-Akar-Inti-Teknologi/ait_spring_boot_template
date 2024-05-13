package org.ait.library.blob.modules.selector.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.ait.library.blob.modules.selector.service.FileStorageService;
import org.ait.library.blob.modules.selector.service.StorageSelectorService;
import org.ait.library.blob.shared.serviceskelenton.EngineStorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

  private final StorageSelectorService storageSelectorService;
  private EngineStorageService engineStorageService;

  @PostConstruct
  private void postConstruct() {
    engineStorageService = storageSelectorService.getEngineStorageService();
  }

  @Override
  public String uploadFile(String fileId, MultipartFile file, String fileDir) {
    return engineStorageService.uploadFile(fileId, file, fileDir);
  }

  @Override
  public String getPublicUrlFile(String fileId, String fileDir) {
    return engineStorageService.getPublicUrl(fileId, fileDir);
  }

  @Override
  public ByteArrayResource downloadFile(String fileId, String fileDir) {
    return engineStorageService.downloadFile(fileId, fileDir);
  }

  @Override
  public void deleteFile(String fileId, String fileDir) {
    engineStorageService.deleteFile(fileId, fileDir);
  }
}
