package org.ait.project.guideline.example.modules.user.model.redis.repository;

import org.ait.project.guideline.example.modules.user.model.redis.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserSession, String> {

}
