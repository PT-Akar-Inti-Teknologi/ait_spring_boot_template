package org.ait.project.guideline.example.modules.user.service.adapter.command.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.ait.project.guideline.example.modules.role.service.adapter.query.RoleQueryAdapter;
import org.ait.project.guideline.example.modules.user.exception.UserNotFoundException;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;
import org.ait.project.guideline.example.modules.user.model.jpa.repository.UserRepository;
import org.ait.project.guideline.example.modules.user.service.adapter.command.UserCommandAdapter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserCommandAdapterImpl implements UserCommandAdapter {

    private final UserRepository userRepository;

    private final RoleQueryAdapter roleQueryAdapter;

    @Override
    public UserAit saveUser(UserAit userAit) {
        return userRepository.save(userAit);
    }

    @Override
    public UserAit updateUser(UserAit userAit) {
        userRepository.findById(userAit.getId()).orElseThrow(UserNotFoundException::new);
        return userRepository.save(userAit);
    }

    @Override
    public UserAit addBalance(Integer userId, BigDecimal amount) {
        UserAit userAit = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userAit.setBalance(userAit.getBalance().add(amount));
        return userRepository.save(userAit);
    }

    @Override
    public void addUserRoles(UserAit userAit, List<Integer> rolesId) {
        if(rolesId.isEmpty()) {
            return;
        }
        List<Role> roles = roleQueryAdapter.findByInIds(rolesId);
        userAit.setRoles(roles);
    }

    @Override
    public void deleteUser(Integer id) {
        UserAit userAit = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userAit);
    }
}
