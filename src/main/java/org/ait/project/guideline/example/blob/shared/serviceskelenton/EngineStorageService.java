package org.ait.project.guideline.example.blob.shared.serviceskelenton;

import org.ait.project.guideline.example.blob.shared.enums.PlatformEnum;
import org.ait.project.guideline.example.blob.shared.exception.StorageFileNotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public interface EngineStorageService {

  boolean platformIs(PlatformEnum platformEnum);

  String uploadFile(String fileId, MultipartFile file, String fileDir);

  ByteArrayResource downloadFile(String fileId, String fileDir) throws StorageFileNotFoundException;

  String getPublicUrl(String fileId, String fileDir) throws StorageFileNotFoundException;

  void deleteFile(String fileId, String fileDir) throws StorageFileNotFoundException;
}
