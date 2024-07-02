package org.ait.project.guideline.example.modules.masterdata.model.specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.masterdata.dto.param.BannerSpecParam;
import org.ait.project.guideline.example.shared.utils.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BannerSpecification extends BaseSpecification {
  @Override
  protected List<String> getDefaultSearchField() {
    return List.of("title", "description");
  }

  public Specification<Banner> predicate(BannerSpecParam param) {
    return (root, query, builder) -> {
      List<Predicate> predicates = new ArrayList<>();

      filterSearch(root, predicates, builder, param.getSearch(), new ArrayList<>());

      return builder.and(predicates.toArray(new Predicate[] {}));
    };
  }
}
