package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class BanerSizeNotValidException extends ModuleException {
  public BanerSizeNotValidException() {
    super(ResponseEnum.BANNER_SIZE_NOT_VALID);
  }
}
