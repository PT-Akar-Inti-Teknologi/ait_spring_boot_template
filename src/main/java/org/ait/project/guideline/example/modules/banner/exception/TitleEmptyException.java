package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class TitleEmptyException extends ModuleException {
  public TitleEmptyException() {
    super(ResponseEnum.TITLE_EMPTY);
  }
}
