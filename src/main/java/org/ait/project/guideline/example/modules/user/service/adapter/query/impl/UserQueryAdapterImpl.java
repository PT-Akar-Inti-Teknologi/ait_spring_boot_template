package org.ait.project.guideline.example.modules.user.service.adapter.query.impl;


import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.masterdata.dto.param.UserParam;
import org.ait.project.guideline.example.modules.masterdata.model.specification.UserSpecification;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.User;
import org.ait.project.guideline.example.modules.user.model.jpa.repository.UserRepository;
import org.ait.project.guideline.example.modules.user.service.adapter.query.UserQueryAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryAdapterImpl implements UserQueryAdapter {

  private final UserRepository repository;

  private final UserSpecification userSpecification;

  @Override
  public Page<User> getPage(Pageable pageable, UserParam userParam) {
    return repository.findAll(userSpecification.predicate(userParam),
        userSpecification.buildPageRequest(pageable));
  }


}
