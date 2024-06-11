package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class DescriptionEmptyException extends ModuleException {
    public DescriptionEmptyException() {
        super(ResponseEnum.DESCRIPTION_EMPTY);
    }
}
