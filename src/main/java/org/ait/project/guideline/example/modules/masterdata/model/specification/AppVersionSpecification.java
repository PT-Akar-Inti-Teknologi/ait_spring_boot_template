package org.ait.project.guideline.example.modules.masterdata.model.specification;

import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.ait.project.guideline.example.modules.appversion.module.jpa.entity.AppVersion;
import org.ait.project.guideline.example.modules.masterdata.dto.param.AppVersionParam;
import org.ait.project.guideline.example.shared.utils.specification.BaseSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppVersionSpecification  extends BaseSpecification {
    @Override
    protected List<String> getDefaultSearchField() {
        return List.of("id","version","platform","type");
    }

    public Specification<AppVersion> predicate(AppVersionParam param) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filterSearch(root, predicates, builder, param.getSearch(), new ArrayList<>());

            return builder.and(predicates.toArray(new Predicate[] {}));
        };
    }

    public PageRequest defaultPageSort(Pageable pageable) {
        return buildPageRequest(pageable, "created_at");
    }
}
