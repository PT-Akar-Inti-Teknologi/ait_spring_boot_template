package org.ait.project.guideline.example.modules.user.service.adapter.command;

import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;

import java.math.BigDecimal;
import java.util.List;

public interface UserCommandAdapter {

    UserAit saveUser(UserAit userAit);

    UserAit updateUser(UserAit userAit);

    UserAit addBalance(Integer userId, BigDecimal amount);

    void addUserRoles(UserAit userAit, List<Integer> rolesId);

    void deleteUser(Integer id);
}
