package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class BannerFileEmptyException extends ModuleException {
    public BannerFileEmptyException() {
        super(ResponseEnum.BANNER_FILE_EMPTY);
    }
}
