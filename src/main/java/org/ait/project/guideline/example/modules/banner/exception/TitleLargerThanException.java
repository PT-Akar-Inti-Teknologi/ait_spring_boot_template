package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class TitleLargerThanException extends ModuleException {
  public TitleLargerThanException() {
    super(ResponseEnum.TITLE_LARGER_THAN);
  }
}
