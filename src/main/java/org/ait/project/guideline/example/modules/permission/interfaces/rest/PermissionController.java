package org.ait.project.guideline.example.modules.permission.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.permission.dto.request.LoginReq;
import org.ait.project.guideline.example.modules.permission.dto.response.LoginRes;
import org.ait.project.guideline.example.modules.permission.service.core.PermissionCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "API Login User")
@RequiredArgsConstructor
@RequestMapping("/auth/login")
public class PermissionController {

    private final PermissionCore permissionCore;

    @Operation(summary = "API to User Login")
    @PostMapping
    public ResponseEntity<ResponseTemplate<ResponseDetail<LoginRes>>> login(@RequestBody LoginReq request) {
        return permissionCore.login(request);
    }
}


