package org.ait.project.guideline.example.modules.banner.service.adapter.query;

import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.masterdata.dto.param.BannerSpecParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BannerQueryAdapter {

    Optional<Banner> getById(String id);

    Page<Banner> getPage(Pageable pageable, BannerSpecParam bannerSpecParam);

}
