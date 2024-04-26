package org.ait.project.guideline.example.modules.appversion.exception;

import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

@Slf4j
public class AppVersionNotFoundException extends ModuleException {

    public AppVersionNotFoundException() {
        super(ResponseEnum.APP_VERSION_NOT_FOUND);
        log.error("Version not found");
    }
}
