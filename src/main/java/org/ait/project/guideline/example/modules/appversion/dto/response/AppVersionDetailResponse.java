package org.ait.project.guideline.example.modules.appversion.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ait.project.guideline.example.shared.constant.enums.TypeAppVersion;

import java.math.BigInteger;

@Data
public class AppVersionDetailResponse {


    @JsonProperty("id")
    private BigInteger id;

    @JsonProperty("version")
    private String version;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("type")
    private TypeAppVersion type;
}
