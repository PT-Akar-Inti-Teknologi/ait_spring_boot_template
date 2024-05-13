package org.ait.library.blob.modules.storageengine.localstorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.library.blob.modules.storageengine.localstorage.config.LocalStorageProp;
import org.ait.library.blob.modules.storageengine.localstorage.exception.LocalStorageCannotAccessPublicException;
import org.ait.library.blob.shared.enums.PlatformEnum;
import org.ait.library.blob.shared.exception.CannotUploadFileException;
import org.ait.library.blob.shared.exception.StorageFileNotFoundException;
import org.ait.library.blob.shared.serviceskelenton.EngineStorageAbstract;
import org.ait.library.blob.shared.utils.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
public class LocalStorageService extends EngineStorageAbstract {

  private final LocalStorageProp localStorageProp;

  @Override
  protected PlatformEnum getPlatform() {
    return PlatformEnum.LOCAL;
  }

  @Override
  public String uploadFile(String fileId, MultipartFile file, String fileDir) {
    log.info("Local Storage Upload");
    log.info(
        String.format("File Upload: id => %s file => %s", fileId, file.getOriginalFilename()));
    fileId = generateFileId(fileId, file);
    File fileToBeUploaded = createFileAddress(fileId,
        FileUtils.getBasePath(localStorageProp.getFilePath()) + FileUtils.getBasePath(fileDir));
    uploadFile(file, fileToBeUploaded);
    log.info(String.format("File uploaded with ID: %s", fileId));
    return fileId;
  }

  private File createFileAddress(String fileId, String path) {
    File fileToBeUploaded = new File(path + fileId);
    try {
      Files.createDirectories(Paths.get(path));
    } catch (IOException e) {
      throw new CannotUploadFileException(e.getMessage());
    }
    return fileToBeUploaded;
  }

  private void uploadFile(MultipartFile file, File fileToBeUploaded) {
    try (FileOutputStream fileOutputStream = new FileOutputStream(fileToBeUploaded)) {
      fileOutputStream.write(file.getBytes());
    } catch (IOException e) {
      throw new CannotUploadFileException(e.getMessage());
    }
  }


  @Override
  public ByteArrayResource downloadFile(String fileId, String fileDir) {
    log.info("Local Storage Download");

    String fileAddress =
        FileUtils.getBasePath(localStorageProp.getFilePath()) + FileUtils.getBasePath(fileDir) +
            fileId;

    log.info(String.format("download file from: %s", fileAddress));

    File file = new File(fileAddress);
    if (!file.exists()) {
      throw new StorageFileNotFoundException();
    }

    Path path = Paths.get(file.getAbsolutePath());
    try {
      return new ByteArrayResource(Files.readAllBytes(path));
    } catch (IOException e) {
      throw new StorageFileNotFoundException();
    }
  }

  @Override
  public String getPublicUrl(String fileId, String fileDir) {
    log.info("Local Storage Get Public URL");

    if (localStorageProp.isAccessPublic()) {

      String fileAddress =
          FileUtils.getBasePath(localStorageProp.getFilePath()) + FileUtils.getBasePath(fileDir) +
              fileId;

      File file = new File(fileAddress);
      if (!file.exists()) {
        throw new StorageFileNotFoundException();
      }

      return FileUtils.getBasePath(localStorageProp.getPublicBaseUrl()) +
          FileUtils.getBasePath(fileDir) + fileId;
    } else {
      throw new LocalStorageCannotAccessPublicException();
    }
  }


  @Override
  public void deleteFile(String fileId, String fileDir) {
    log.info("Local Storage Delete File");
    String fileAddress =
        FileUtils.getBasePath(localStorageProp.getFilePath()) + FileUtils.getBasePath(fileDir) +
            fileId;
    try {
      Files.deleteIfExists(Paths.get(fileAddress));
    } catch (IOException e) {
      throw new StorageFileNotFoundException();
    }
  }
}
