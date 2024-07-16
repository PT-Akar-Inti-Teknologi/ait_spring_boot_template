package org.ait.project.guideline.example.modules.banner.service.adapter.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.banner.dto.request.BannerSortReq;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.banner.model.jpa.repository.BannerRepository;
import org.ait.project.guideline.example.modules.banner.service.adapter.command.BannerCommandAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  @Transactional
  public void resortingData(List<BannerSortReq> bannerSortReqs) {
    List<Banner> bannerList = new ArrayList<>();
    bannerSortReqs.forEach(bannerSortReq -> {
      bannerRepository.findById(bannerSortReq.getId()).ifPresent(banner -> {
         banner.setIndex(bannerSortReq.getIndex());
         bannerList.add(banner);
      });
    });
    bannerRepository.saveAll(bannerList);
  }
}
