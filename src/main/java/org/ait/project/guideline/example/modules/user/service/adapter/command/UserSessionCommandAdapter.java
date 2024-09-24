package org.ait.project.guideline.example.modules.user.service.adapter.command;


public interface UserSessionCommandAdapter {

    void upsertUserSession(Integer userId, Long timeToLive);

    void deleteSession(String userId);
}
