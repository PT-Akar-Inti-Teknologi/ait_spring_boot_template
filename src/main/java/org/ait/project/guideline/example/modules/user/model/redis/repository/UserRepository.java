package org.ait.project.guideline.example.modules.user.model.redis.repository;

import org.ait.project.guideline.example.modules.user.model.redis.entity.UserAit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAit, Integer> {

    Optional<UserAit> findByEmail(String email);
}
