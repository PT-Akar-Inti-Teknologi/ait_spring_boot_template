package org.ait.project.guideline.example.modules.permission.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.permission.dto.request.LoginReq;
import org.ait.project.guideline.example.modules.permission.dto.response.LoginRes;
import org.ait.project.guideline.example.modules.permission.service.core.PermissionCore;
import org.ait.project.guideline.example.modules.user.service.adapter.query.UserQueryAdapter;
import org.ait.project.guideline.example.modules.user.service.core.UserCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionCoreImpl implements PermissionCore {

    private final UserQueryAdapter userQueryAdapter;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<LoginRes>>> login(LoginReq request) {

        return null;
    }
}
