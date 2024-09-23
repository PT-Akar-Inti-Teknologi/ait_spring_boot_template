package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class BanerSizeOverException extends ModuleException {
  public BanerSizeOverException() {
    super(ResponseEnum.BANNER_SIZE_OVER);
  }
}
