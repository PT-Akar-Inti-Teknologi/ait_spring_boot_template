package org.ait.project.guideline.example.modules.banner.service.adapter.command;

import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;

public interface BannerCommandAdapter {

    Banner save(Banner banner);

    void delete(Banner banner);

}
