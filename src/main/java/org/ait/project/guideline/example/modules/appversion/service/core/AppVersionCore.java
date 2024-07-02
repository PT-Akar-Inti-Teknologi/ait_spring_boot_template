package org.ait.project.guideline.example.modules.appversion.service.core;

import java.math.BigInteger;
import java.util.List;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionDetailResponse;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionTypeResponse;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AppVersionCore {

  ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionTypeResponse>>> getAppVersion(String version, String platform);
  ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionDetailResponse>>> getDetail(BigInteger id);

  ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> getAllAppVersion(
      Pageable pageable, AppVersionParam appVersionParam);

  ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> saveAppVersions(
      List<AppVersionDetailResponse> appVersionRequests);

  ResponseEntity<ResponseTemplate<ResponseDetail<Boolean>>> deleteAppVersion(List<BigInteger> ids);

}
