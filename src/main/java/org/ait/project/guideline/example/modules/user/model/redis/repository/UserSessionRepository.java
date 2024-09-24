package org.ait.project.guideline.example.modules.user.model.redis.repository;

import org.ait.project.guideline.example.modules.user.model.redis.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserSessionRepository extends CrudRepository<UserSession, String> {

}
