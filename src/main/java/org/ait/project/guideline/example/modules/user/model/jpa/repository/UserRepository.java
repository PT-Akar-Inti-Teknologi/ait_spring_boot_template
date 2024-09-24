package org.ait.project.guideline.example.modules.user.model.jpa.repository;

import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserAit, Integer> {

    Optional<UserAit> findByEmail(String email);
    Optional<UserAit> findByUsername(String username);
}
