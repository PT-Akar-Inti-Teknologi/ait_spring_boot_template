package org.ait.project.guideline.example.blob.shared.serviceskelenton;

import org.ait.project.guideline.example.blob.shared.enums.PlatformEnum;
import org.ait.project.guideline.example.blob.shared.utils.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public abstract class EngineStorageAbstract implements EngineStorageService {

  protected abstract PlatformEnum getPlatform();

  protected String generateFileId(String fileIdFilled, MultipartFile file) {
    if (fileIdFilled == null) {
      return FileUtils.generateFileId(getPlatform(), file);
    }
    if (FileUtils.hasExtention(fileIdFilled)) {
      return fileIdFilled;
    }
    return String.format("%s.%s", fileIdFilled, FileUtils.getFileExtention(file));
  }

  @Override
  public boolean platformIs(PlatformEnum platformEnum) {
    return platformEnum == getPlatform();
  }
}
