package org.ait.project.guideline.example.modules.permission.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.config.properties.ApplicationProperties;
import org.ait.project.guideline.example.modules.permission.service.core.UserSessionService;
import org.ait.project.guideline.example.modules.user.service.adapter.command.UserSessionCommandAdapter;
import org.springframework.stereotype.Service;

/**
 * @author febrihasan
 */
@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionCommandAdapter userSessionCommandAdapter;

    private final ApplicationProperties properties;

    @Override
    public void upsertUserSession(Integer userId) {
        userSessionCommandAdapter.upsertUserSession(userId,
                properties.getSessionPorperties().getOnlineSessionInSeconds());
    }
}
