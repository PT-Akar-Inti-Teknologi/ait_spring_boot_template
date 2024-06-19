package org.ait.project.guideline.example.modules.appversion.exception;

import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

@Slf4j
public class AppVersionDuplicateKeyValueException extends ModuleException {

  public AppVersionDuplicateKeyValueException() {
    super(ResponseEnum.APP_VERSION_DUPLICATE_KEY);
    log.error("Version duplicate");
  }
}
