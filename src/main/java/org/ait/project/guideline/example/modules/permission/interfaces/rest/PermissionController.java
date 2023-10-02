package org.ait.project.guideline.example.modules.permission.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.permission.service.core.PermissionCore;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PermissionController implements PermissionCore {

    private final PermissionCore permissionCore;

}
