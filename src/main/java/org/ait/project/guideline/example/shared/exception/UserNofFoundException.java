package org.ait.project.guideline.example.shared.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class UserNofFoundException extends ModuleException {
  public UserNofFoundException() {
    super(ResponseEnum.USER_NOT_FOUND);
  }
}
