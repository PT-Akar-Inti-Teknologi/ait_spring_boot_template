package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class BannerNotFoundException extends ModuleException {
  public BannerNotFoundException() {
    super(ResponseEnum.BANNER_NOT_FOUND);
  }
}
