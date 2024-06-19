package org.ait.project.guideline.example.modules.user.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.masterdata.dto.param.UserParam;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.service.core.UserCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "API User CRUD")
@RequestMapping("/user")
public class UserController {
  private final UserCore userCore;


  @Operation(summary = "API to get All Users")
  @GetMapping("/all")
  public ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers(
      Pageable pageable,
      UserParam userParam) {

    return userCore.getUsers(pageable, userParam);
  }

}
