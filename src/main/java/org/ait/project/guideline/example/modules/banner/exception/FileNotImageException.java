package org.ait.project.guideline.example.modules.banner.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class FileNotImageException extends ModuleException {
    public FileNotImageException() {
        super(ResponseEnum.FILE_NOT_IMAGE);
    }
}
