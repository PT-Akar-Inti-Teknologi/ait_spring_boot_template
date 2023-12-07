package org.ait.project.guideline.example.modules.user.service.adapter.query.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;
import org.ait.project.guideline.example.modules.user.model.jpa.repository.UserRepository;
import org.ait.project.guideline.example.modules.user.service.adapter.query.UserQueryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryAdapterImpl implements UserQueryAdapter {

    private final UserRepository userRepository;

    @Override
    public List<UserAit> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserAit> findUserById(Integer id) {
        return userRepository.findById(id);
    }
}
