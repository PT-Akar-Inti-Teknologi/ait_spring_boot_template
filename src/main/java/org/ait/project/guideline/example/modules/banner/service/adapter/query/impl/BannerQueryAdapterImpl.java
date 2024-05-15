package org.ait.project.guideline.example.modules.banner.service.adapter.query.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.banner.model.jpa.repository.BannerRepository;
import org.ait.project.guideline.example.modules.banner.service.adapter.query.BannerQueryAdapter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerQueryAdapterImpl implements BannerQueryAdapter {

    private final BannerRepository bannerRepository;

    @Override
    public Optional<Banner> getById(String id) {
        return bannerRepository.findById(id);
    }

}
