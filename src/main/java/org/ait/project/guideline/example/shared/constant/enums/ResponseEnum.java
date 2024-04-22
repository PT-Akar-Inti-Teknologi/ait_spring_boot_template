package org.ait.project.guideline.example.shared.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
  SUCCESS("PMRK-200", "success", HttpStatus.OK),
  JSON_PLACE_HOLDER_POST_NOT_FOUND("PMRK-0001", "jph.post.not.found", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND("PMRK-0002","user.not.found", HttpStatus.NOT_FOUND),
  ROLE_NOT_FOUND("PMRK-0003","role.not.found" ,HttpStatus.NOT_FOUND),
  INVALID_PARAM("PMRK-0009", "invalid.param", HttpStatus.BAD_REQUEST),
  INTERNAL_SERVER_ERROR("PMRK-9999", "internal.server.error", HttpStatus.INTERNAL_SERVER_ERROR),

  //App-Version
  APP_VERSION_NOT_FOUND("TEST-0002","app-version.not.found", HttpStatus.NOT_FOUND);




  private String responseCode;
  private String responseMessage;
  private HttpStatus httpStatus;

}
