package org.ait.project.guideline.example.utils.specification;

import com.google.common.base.CaseFormat;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@UtilityClass
public class SpecificationUtils {

  public PageRequest buildPageRequest(Pageable pageable) {
    return PageRequest.of(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        buildSortRequest(pageable.getSort())
    );
  }

  public Sort buildSortRequest(Sort sort) {
    return Sort.by(sort.get()
        .map(sortOrder -> sortOrder.withProperty(
            CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, sortOrder.getProperty()))
        ).toList());
  }
}
