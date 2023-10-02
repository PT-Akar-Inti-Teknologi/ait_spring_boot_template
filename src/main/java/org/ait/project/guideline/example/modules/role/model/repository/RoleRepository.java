package org.ait.project.guideline.example.modules.role.model.repository;

import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Page<Role> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
