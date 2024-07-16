package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class BanerSizeEmptyException extends ModuleException {
  public BanerSizeEmptyException() {
    super(ResponseEnum.BANNER_SIZE_NOT_VALID);
  }
}
