package org.ait.project.guideline.example.modules.permission.service.core;

import org.ait.project.guideline.example.modules.permission.dto.request.LoginReq;
import org.ait.project.guideline.example.modules.permission.dto.response.LoginRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;

public interface PermissionCore {

    ResponseEntity<ResponseTemplate<ResponseDetail<LoginRes>>> login(LoginReq request);

}
