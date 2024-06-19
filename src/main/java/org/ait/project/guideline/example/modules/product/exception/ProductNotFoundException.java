package org.ait.project.guideline.example.modules.product.exception;

import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

@Slf4j
public class ProductNotFoundException extends ModuleException {

  private static final long serialVersionUID = 1L;

  public ProductNotFoundException() {
    super(ResponseEnum.USER_NOT_FOUND);
    log.error("User not found");
  }
}
