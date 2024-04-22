package org.ait.project.guideline.example.modules.masterdata.model.specification;

import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.shared.utils.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppVersionSpecification  extends BaseSpecification {
    @Override
    protected List<String> getDefaultSearchField() {
        return List.of("id","version","platform","force_update","soft_update");
    }

    public Specification<AppVersion> predicate(AppVersionParam param) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            List<Predicate> customSearchPredicate = new ArrayList<>();
            customSearchPredicate.add(builder.like(builder.lower(root.get("version").as(String.class)),
                    ("%" + param.getSearch() + "%").toLowerCase()));

            filterSearch(root, predicates, builder, param.getSearch(), customSearchPredicate);

            Order order = builder.asc(root.get("id"));
            query.orderBy(order);

            return builder.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
