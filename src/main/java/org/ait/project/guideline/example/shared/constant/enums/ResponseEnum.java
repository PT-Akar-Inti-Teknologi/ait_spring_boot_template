package org.ait.project.guideline.example.shared.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
  SUCCESS("PMRK-200", "success", HttpStatus.OK),
  JSON_PLACE_HOLDER_POST_NOT_FOUND("PMRK-0001", "jph.post.not.found", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND("PMRK-0002", "user.not.found", HttpStatus.NOT_FOUND),
  ROLE_NOT_FOUND("PMRK-0003", "role.not.found", HttpStatus.NOT_FOUND),
  BANNER_NOT_FOUND("PMRK-0014", "banner.not.found", HttpStatus.NOT_FOUND),
  BANNER_FILE_EMPTY("PMRK-0015", "banner.file.empty", HttpStatus.BAD_REQUEST),
  FILE_NOT_IMAGE("PMRK-0016", "file.not.image", HttpStatus.BAD_REQUEST),
  TITLE_EMPTY("PMRK-0017", "tittle.cannot.be.empty", HttpStatus.BAD_REQUEST),
  TITLE_LARGER_THAN("PMRK-0018", "tittle.larger.than", HttpStatus.BAD_REQUEST),
  DESCRIPTION_EMPTY("PMRK-0019", "description.cannot.be.empty", HttpStatus.BAD_REQUEST),
  INVALID_PARAM("PMRK-0009", "invalid.param", HttpStatus.BAD_REQUEST),
  INTERNAL_SERVER_ERROR("PMRK-9999", "internal.server.error", HttpStatus.INTERNAL_SERVER_ERROR),

  //App-Version
  APP_VERSION_NOT_FOUND("APPV-0002", "app-version.not.found", HttpStatus.NOT_FOUND),
  APP_VERSION_DUPLICATE_KEY("APPV-0003", "app-version.duplicate.key", HttpStatus.CONFLICT),
  ;


  private String responseCode;
  private String responseMessage;
  private HttpStatus httpStatus;

}
