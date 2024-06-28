package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class FileContentNotImageException extends ModuleException {
  public FileContentNotImageException() {
    super(ResponseEnum.FILE_CONTENT_NOT_IMAGE);
  }
}
