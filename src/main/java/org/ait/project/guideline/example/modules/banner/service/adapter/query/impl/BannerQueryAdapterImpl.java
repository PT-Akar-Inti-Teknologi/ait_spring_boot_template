package org.ait.project.guideline.example.modules.banner.service.adapter.query.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.banner.model.jpa.repository.BannerRepository;
import org.ait.project.guideline.example.modules.banner.service.adapter.query.BannerQueryAdapter;
import org.ait.project.guideline.example.modules.masterdata.dto.param.BannerSpecParam;
import org.ait.project.guideline.example.modules.masterdata.model.specification.BannerSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerQueryAdapterImpl implements BannerQueryAdapter {

  private final BannerRepository bannerRepository;

  private final BannerSpecification bannerSpecification;

  @Override
  public Optional<Banner> getById(String id) {
    return bannerRepository.findById(id);
  }

  @Override
  public Page<Banner> getPage(Pageable pageable, BannerSpecParam bannerSpecParam) {
    return bannerRepository.findAll(bannerSpecification.predicate(bannerSpecParam),
        bannerSpecification.defaultPageSort(pageable));
  }

  @Override
  public List<Banner> getAllActiveBanner() {
    return bannerRepository.findByIsActiveTrueOrderByIndexDesc();
  }

  @Override
  public Long getCountBanner() {
    return bannerRepository.count();
  }
}
