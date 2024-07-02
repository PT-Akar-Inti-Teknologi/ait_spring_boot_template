package org.ait.project.guideline.example.utils.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.utils.specification.entity.Search;
import org.ait.project.guideline.example.utils.specification.repository.SearchRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MainSearch {

  private static final String SEARCH = "%%%s%%";
  private static final String COMA = ",";
  private final SearchRepository repository;

  @SuppressWarnings("rawtypes")
  public <T> Specification build(String search, String className) {

    Search data = repository.findByClassName(className);
    List<String> searchField = Arrays.asList(data.getSearchField().split(COMA));

    return hasField(search, searchField);

  }

  private Specification<Object> hasField(String search, List<String> searchField) {

    return (root, cq, cb) -> {
      if (ObjectUtils.isEmpty(search)) {
        return cb.conjunction();
      }
      List<Predicate> predicates = this.filterSearch(root, cb, search, searchField);
      return cb.or(predicates.toArray(new Predicate[] {}));

    };
  }

  public <T> List<Predicate> filterSearch(Root<T> root, CriteriaBuilder builder, String search,
                                          List<String> searchField) {
    List<Predicate> predicateList = new ArrayList<>();
    if (StringUtils.hasLength(search)) {
      String searchPredicate = String.format(SEARCH, search).toLowerCase(Locale.ROOT);
      searchField.forEach(
          e -> predicateList.add(
              builder.like(builder.lower(root.get(e).as(String.class)), searchPredicate)));
      predicateList.add(builder.or(predicateList.toArray(new Predicate[0])));
    }
    return predicateList;
  }


}
