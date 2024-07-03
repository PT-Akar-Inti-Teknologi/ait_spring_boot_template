package org.ait.project.guideline.example.modules.appversion.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ait.project.guideline.example.shared.constant.enums.TypeAppVersion;

import java.math.BigInteger;

@Data
public class AppVersionDetailRequest {


  @JsonProperty("version")
  private String version;

  @JsonProperty("platform")
  private String platform;

  @JsonProperty("type")
  private TypeAppVersion type;
}
