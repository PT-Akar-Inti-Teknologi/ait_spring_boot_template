package org.ait.project.guideline.example.modules.user.service.core;

import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;


public interface UserCore {

    ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers(String searchBy, String sortBy, Integer pageNumber, Integer pageSize, UserReq userReq);

}
