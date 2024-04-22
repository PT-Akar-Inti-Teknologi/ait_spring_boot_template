package org.ait.project.guideline.example.modules.appversion.dto.request;

import lombok.Data;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionDetailResponse;

import java.util.List;

@Data
public class AppVersionRequest {

    private List<AppVersionDetailResponse> appVersions;

}
