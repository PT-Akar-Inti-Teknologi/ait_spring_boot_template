package org.ait.project.guideline.example.modules.banner.model.jpa.repository;

import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository
    extends JpaRepository<Banner, String>, JpaSpecificationExecutor<Banner> {
}
