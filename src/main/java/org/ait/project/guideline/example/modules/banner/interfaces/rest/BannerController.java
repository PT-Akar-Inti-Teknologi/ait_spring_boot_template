package org.ait.project.guideline.example.modules.banner.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.modules.banner.service.core.BannerCore;
import org.ait.project.guideline.example.modules.masterdata.dto.param.BannerSpecParam;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "API Banner CRUD")
@RequestMapping("/banner")
@Slf4j
public class BannerController {

  private final BannerCore bannerCore;


  @Operation(summary = "API Post Banner")
  @PostMapping
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(
      @RequestParam(value = "file", required = false) MultipartFile file,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "description", required = false) String description) {
    return bannerCore.upload(new BannerParam(file, title, description));
  }

  @Operation(summary = "Get Detail Banner")
  @GetMapping("/{id}")
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> detailBanner(
      @PathVariable("id") String id) {
    return bannerCore.getDetailBanner(id);
  }

  @Operation(summary = "API Download Banner")
  @GetMapping("/download")
  public ResponseEntity<Resource> download(@RequestParam("id") String id) {
    return bannerCore.download(id);
  }

  @Operation(summary = "API Download Thumbnail Banner")
  @GetMapping("/download-thumbnail")
  public ResponseEntity<Resource> downloadThumbnail(@RequestParam("id") String id) {
    return bannerCore.downloadThumbnail(id);
  }

  @Operation(summary = "API Update Banner")
  @PutMapping
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> updateBanner(
      @RequestParam("id") String id,
      @RequestParam(value = "file", required = false) MultipartFile file,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "description", required = false) String description) {
    return bannerCore.update(id, new BannerParam(file, title, description));
  }

  @Operation(summary = "API Delete Banner")
  @DeleteMapping
  public ResponseEntity<ResponseTemplate<ResponseDetail<Object>>> deleteBanner(
      @RequestParam("id") String id) {
    return bannerCore.delete(id);
  }

  @Operation(summary = "API to get All Banner")
  @GetMapping("/all")
  public ResponseEntity<ResponseTemplate<ResponseCollection<BannerRes>>> getAllVersion(
      Pageable pageable, BannerSpecParam versionParam) {
    return bannerCore.getAllBanner(pageable, versionParam);
  }
}
