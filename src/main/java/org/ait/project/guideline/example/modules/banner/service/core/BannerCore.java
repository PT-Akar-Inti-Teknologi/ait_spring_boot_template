package org.ait.project.guideline.example.modules.banner.service.core;

import java.util.List;
import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.request.BannerSortReq;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.modules.masterdata.dto.param.BannerSpecParam;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BannerCore {

  ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(BannerParam param);

  ResponseEntity<Resource> download(String id);

  ResponseEntity<Resource> downloadThumbnail(String id);

  ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> update(String id, BannerParam param);

  ResponseEntity<ResponseTemplate<ResponseDetail<Object>>> delete(String id);

  ResponseEntity<ResponseTemplate<ResponseCollection<BannerRes>>> getAllBanner(Pageable pageable,
                                                                               BannerSpecParam bannerSpecParam);
  ResponseEntity<ResponseTemplate<ResponseCollection<BannerRes>>> getAllActiveBanner();

  ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> getDetailBanner(String id);

  ResponseEntity<ResponseTemplate<ResponseDetail<String>>> sortingBanner(
      List<BannerSortReq> bannerSortReqs);
}
