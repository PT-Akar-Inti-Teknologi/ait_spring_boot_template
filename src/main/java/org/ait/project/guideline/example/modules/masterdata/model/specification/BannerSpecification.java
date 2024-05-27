package org.ait.project.guideline.example.modules.masterdata.model.specification;

import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.masterdata.dto.param.BannerSpecParam;
import org.ait.project.guideline.example.shared.utils.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BannerSpecification  extends BaseSpecification {
    @Override
    protected List<String> getDefaultSearchField() {
        return List.of("id","title","description","image_file","thumbnail_file");
    }

    public Specification<Banner> predicate(BannerSpecParam param) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            List<Predicate> customSearchPredicate = new ArrayList<>();
            customSearchPredicate.add(builder.like(builder.lower(root.get("title").as(String.class)),
                    ("%" + param.getSearch() + "%").toLowerCase()));

            filterSearch(root, predicates, builder, param.getSearch(), customSearchPredicate);

            Order order = builder.asc(root.get("id"));
            query.orderBy(order);

            return builder.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
