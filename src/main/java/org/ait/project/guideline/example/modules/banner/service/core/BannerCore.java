package org.ait.project.guideline.example.modules.banner.service.core;

import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;

public interface BannerCore {

    ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(BannerParam param);

    ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> getBanner(String id);

    ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> update(String id, BannerParam param);

    ResponseEntity<ResponseTemplate<ResponseDetail<Object>>> delete(String id);

}
