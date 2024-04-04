package org.ait.project.guideline.example.shared.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class InvalidJwtException extends ModuleException {
  public InvalidJwtException() {
    super(ResponseEnum.INVALID_JWT);
  }
}
