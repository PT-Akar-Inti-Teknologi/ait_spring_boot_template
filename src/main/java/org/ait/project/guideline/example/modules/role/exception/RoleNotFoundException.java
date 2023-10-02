package org.ait.project.guideline.example.modules.role.exception;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;

public class RoleNotFoundException extends ModuleException {

    public RoleNotFoundException() {
        super(ResponseEnum.ROLE_NOT_FOUND);
    }
}
