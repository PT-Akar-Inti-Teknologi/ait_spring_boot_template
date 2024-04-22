package org.ait.project.guideline.example.modules.appversion.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ait.project.guideline.example.shared.constant.enums.TypeAppVersion;

@Data
public class AppVersionTypeResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("type")
    private TypeAppVersion type;

}
