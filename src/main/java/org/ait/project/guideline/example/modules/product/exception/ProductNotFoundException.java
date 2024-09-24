package org.ait.project.guideline.example.modules.product.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class ProductNotFoundException extends ModuleException {

    public ProductNotFoundException() {
        super(ResponseEnum.PRODUCT_NOT_FOUND);
    }
}
