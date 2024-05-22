package org.ait.project.guideline.example.modules.banner.service.adapter.query.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.banner.model.jpa.repository.BannerRepository;
import org.ait.project.guideline.example.modules.banner.service.adapter.query.BannerQueryAdapter;
import org.ait.project.guideline.example.modules.masterdata.dto.param.BannerSpecParam;
import org.ait.project.guideline.example.modules.masterdata.model.specification.BannerSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                bannerSpecification.buildPageRequest(pageable));
    }

}