package org.ait.project.guideline.example.modules.permission.service.adapter.command;

import org.ait.project.guideline.example.modules.permission.model.entity.Permission;
import org.ait.project.guideline.example.modules.role.model.entity.Role;

import java.util.List;

public interface PermissionCommandAdapter {

    Permission savePermission(Permission permission);

    List<Permission> savePermissions(List<Permission> permissions);

    void deletePermissionByRole(Role roleId);

}
