package org.ait.project.guideline.example.modules.permission.model.repository;

import org.ait.project.guideline.example.modules.permission.model.entity.Permission;
import org.ait.project.guideline.example.modules.permission.model.entity.key.PermissionId;
import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, PermissionId> {

    void deleteByIdRole(Role role);


}
