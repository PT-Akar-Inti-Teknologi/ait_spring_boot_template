package org.ait.project.guideline.example.modules.user.service.core;

import org.ait.project.guideline.example.modules.masterdata.dto.param.UserParam;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface UserCore {

    ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers(Pageable pageable, UserParam userParam);

}
