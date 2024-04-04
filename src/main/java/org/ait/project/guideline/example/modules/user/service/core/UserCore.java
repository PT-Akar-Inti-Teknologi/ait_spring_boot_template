package org.ait.project.guideline.example.modules.user.service.core;

import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;


public interface UserCore {

    ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> addUser(UserReq userReq);

    ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers();

    ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> getDetailUser(Integer id);

    ResponseEntity<ResponseTemplate<ResponseDetail<String>>> fetchUser();

    ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> updateUser(Integer id, UserReq userReq);

    ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> addBalance(Integer id, UserReq userReq);

    ResponseEntity<ResponseTemplate<ResponseDetail<String>>> deleteUser(Integer id);

}
