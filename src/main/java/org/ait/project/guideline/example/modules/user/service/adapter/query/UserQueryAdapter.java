package org.ait.project.guideline.example.modules.user.service.adapter.query;

import org.ait.project.guideline.example.modules.user.model.entity.UserAit;

import java.util.List;
import java.util.Optional;

public interface UserQueryAdapter {

    List<UserAit> findAllUser();

    Optional<UserAit> findUserById(Integer id);


}
