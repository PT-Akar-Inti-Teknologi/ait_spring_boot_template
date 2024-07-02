package org.ait.project.guideline.example.modules.appversion.dto.request;

import java.util.List;
import lombok.Data;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionDetailResponse;

@Data
public class AppVersionRequest {

  private List<AppVersionDetailResponse> appVersions;

}
