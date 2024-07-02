package org.ait.project.guideline.example.modules.appversion.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.appversion.dto.request.AppVersionRequest;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionDetailResponse;
import org.ait.project.guideline.example.modules.appversion.dto.response.AppVersionTypeResponse;
import org.ait.project.guideline.example.modules.appversion.service.core.AppVersionCore;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "API App Version CRUD")
@RequestMapping("/app-version")
public class AppVersionController {

  private final AppVersionCore appVersionCore;

  @Operation(summary = "API to get Detail Version")
  @GetMapping("/detail")
  public ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionTypeResponse>>> getDetailVersion(
      @RequestParam("version") String version,
      @RequestParam("platform") String platform) {
    return appVersionCore.getAppVersion(version, platform);
  }

    @Operation(summary = "API to Check Version")
    @GetMapping("/check")
    public ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionTypeResponse>>> checkVersion(
            @RequestParam("version") String version,
            @RequestParam("platform") String platform){
        return appVersionCore.getAppVersion(version, platform);
    }

    @Operation(summary = "API to get Detail by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<AppVersionDetailResponse>>> getDetail(
            @PathVariable("id") BigInteger id) {
        return appVersionCore.getDetail(id);
    }

  @Operation(summary = "API to get All Version")
  @GetMapping("/all")
  public ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> getAllVersion(
      Pageable pageable,
      AppVersionParam versionParam) {
    return appVersionCore.getAllAppVersion(pageable, versionParam);
  }

  @Operation(summary = "API to post App Version")
  @PostMapping("/save")
  public ResponseEntity<ResponseTemplate<ResponseCollection<AppVersionDetailResponse>>> saveAppVersions(
      @RequestBody AppVersionRequest request) {
    return appVersionCore.saveAppVersions(request.getAppVersions());
  }

  @Operation(summary = "API to delete App Version")
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseTemplate<ResponseDetail<Boolean>>> deleteAppVersion(
      @RequestParam("ids") List<BigInteger> ids) {
    return appVersionCore.deleteAppVersion(ids);
  }


}
