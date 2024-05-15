package org.ait.project.guideline.example.modules.banner.service.adapter.query;

import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;

import java.util.Optional;

public interface BannerQueryAdapter {

    Optional<Banner> getById(String id);

}
