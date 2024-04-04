package org.ait.project.guideline.example.modules.user.service.adapter.command.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.user.model.redis.entity.UserSession;
import org.ait.project.guideline.example.modules.user.model.redis.repository.UserRepository;
import org.ait.project.guideline.example.modules.user.service.adapter.command.UserSessionCommandAdapter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author febrihasan
 */
@Service
@RequiredArgsConstructor
public class UserSessionCommandAdapterImpl implements UserSessionCommandAdapter {

    private final UserRepository userSessionRepository;

    @Override
    public void upsertUserSession(Integer userId, Long timeToLive) {
        userSessionRepository.save(new UserSession(userId, LocalDateTime.now(), timeToLive));
    }

    @Override
    public void deleteSession(String userId) {
        userSessionRepository.deleteById(userId);
    }
}
