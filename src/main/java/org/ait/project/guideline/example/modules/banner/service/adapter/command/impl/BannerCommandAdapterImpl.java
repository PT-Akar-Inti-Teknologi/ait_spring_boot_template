package org.ait.project.guideline.example.modules.banner.service.adapter.command.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.banner.model.jpa.repository.BannerRepository;
import org.ait.project.guideline.example.modules.banner.service.adapter.command.BannerCommandAdapter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerCommandAdapterImpl implements BannerCommandAdapter {

  private final BannerRepository bannerRepository;

  @Override
  public Banner save(Banner banner) {
    if (banner.getId() == null) {
      banner.setId(UUID.randomUUID().toString());
    }
    return bannerRepository.save(banner);
  }

  @Override
  public void delete(Banner banner) {
    bannerRepository.delete(banner);
  }

}
