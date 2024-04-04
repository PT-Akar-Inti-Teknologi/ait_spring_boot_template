package org.ait.project.guideline.example.shared.utils.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.*;

import org.ait.project.guideline.example.shared.constant.enums.approval.ActionEnum;
import org.ait.project.guideline.example.shared.constant.enums.approval.StatusEnum;
import org.ait.project.guideline.example.utils.specification.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public abstract class BaseSpecification {

  private static final String SEARCH = "%%%s%%";

  protected abstract List<String> getDefaultSearchField();

  public <T> void filterSearch(Root<T> root, List<Predicate> predicates, CriteriaBuilder builder,
                               String search, List<Predicate> customPredicates,
                               String... customFields) {
    if (StringUtils.hasLength(search)) {
      String searchPredicate = String.format(SEARCH, search).toLowerCase(Locale.ROOT);
      List<Predicate> predicateList = new ArrayList<>(customPredicates);
      if (customFields.length > 0) {
        Arrays.stream(customFields).toList().forEach(
            e -> predicateList.add(builder.like(builder.lower(root.get(e).as(String.class)), searchPredicate)));
      } else {
        getDefaultSearchField().forEach(
            e -> predicateList.add(builder.like(builder.lower(root.get(e).as(String.class)), searchPredicate)));
      }
      predicates.add(builder.or(predicateList.toArray(new Predicate[0])));
    }
  }

  public <T> void filterStatus(Root<T> root, List<Predicate> predicates, CriteriaBuilder builder,
                               List<String> statusList) {
    List<Predicate> predicateStatus = new ArrayList<>();
    if (!statusList.isEmpty()) {
      List<StatusEnum> statusEnums = statusList.stream()
              .map(StatusEnum::valueOf).toList();
      predicateStatus.add(root.get("status").in(statusEnums));
      predicates.add(builder.or(predicateStatus.toArray(new Predicate[0])));
    } else {
      predicates.add(builder.notEqual(root.get("status"), StatusEnum.APPROVED));
    }
  }

  public <T> void filterGlobalAction(Root<T> root, List<Predicate> predicates,
                                     CriteriaBuilder builder, List<String> actionList) {
    List<Predicate> predicateAction = new ArrayList<>();
    if (!actionList.isEmpty()) {
      List<ActionEnum> actionEnums = actionList.stream()
              .map(ActionEnum::valueOf).toList();
      predicateAction.add(root.get("action").in(actionEnums));
      predicates.add(builder.or(predicateAction.toArray(new Predicate[0])));
    }
  }

  public PageRequest buildPageRequest(Pageable pageable) {
    return SpecificationUtils.buildPageRequest(replaceSort(pageable, null));
  }

  public PageRequest buildPageRequest(Pageable pageable, String attribute) {
    return SpecificationUtils.buildPageRequest(replaceSort(pageable, attribute));
  }

  private Pageable replaceSort(Pageable pageable, String attribute) {
    Sort sort = pageable.getSort();
    if(sort.isSorted()) {
      Sort newSort = Sort.by(sort.get().map(this::updateSortProperty).toList());
      return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
    } else if (Objects.nonNull(attribute)) {
      Sort newSort = Sort.by(Sort.Direction.DESC, attribute);
      return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
    }
    return pageable;
  }

  private Sort.Order updateSortProperty(Sort.Order order) {
    String property = order.getProperty();
    return new Sort.Order(order.getDirection(), property);
  }
}
