package org.ait.project.guideline.example.shared.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class InvalidUserPassException extends ModuleException {
  public InvalidUserPassException() {
    super(ResponseEnum.INVALID_USER_PASSWORD);
  }
}
