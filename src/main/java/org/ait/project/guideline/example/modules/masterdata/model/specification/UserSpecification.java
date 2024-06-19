package org.ait.project.guideline.example.modules.masterdata.model.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.ait.project.guideline.example.modules.masterdata.dto.param.UserParam;
import org.ait.project.guideline.example.modules.role.model.jpa.entity.Role;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.User;
import org.ait.project.guideline.example.shared.constant.statics.StaticConstant;
import org.ait.project.guideline.example.shared.utils.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
public class UserSpecification extends BaseSpecification {

  @Override
  protected List<String> getDefaultSearchField() {
    return List.of("username", "email", "phoneNumber", "firstName", "lastName");
  }

  public Specification<User> predicate(UserParam param) {
    return (root, query, builder) -> {
      List<Predicate> predicates = new ArrayList<>();
      List<Predicate> customSearchPredicate = new ArrayList<>();
      Join<Role, User> userJoin = root.join(StaticConstant.ROLE_ID);
      customSearchPredicate.add(builder.like(builder.lower(userJoin.get("name").as(String.class)),
          ("%" + param.getSearch() + "%").toLowerCase()));

      filterSearch(root, predicates, builder, param.getSearch(), customSearchPredicate);

      return builder.and(predicates.toArray(new Predicate[] {}));
    };
  }

}
