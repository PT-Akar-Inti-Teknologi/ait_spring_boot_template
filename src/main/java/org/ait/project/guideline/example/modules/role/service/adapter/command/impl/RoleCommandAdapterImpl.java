package org.ait.project.guideline.example.modules.role.service.adapter.command.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.permission.model.entity.Permission;
import org.ait.project.guideline.example.modules.permission.model.entity.key.PermissionId;
import org.ait.project.guideline.example.modules.permission.service.adapter.command.PermissionCommandAdapter;
import org.ait.project.guideline.example.modules.permission.transform.PermissionTransform;
import org.ait.project.guideline.example.modules.role.dto.request.RoleReq;
import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.ait.project.guideline.example.modules.role.model.repository.RoleRepository;
import org.ait.project.guideline.example.modules.role.service.adapter.command.RoleCommandAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleCommandAdapterImpl implements RoleCommandAdapter {

    private final RoleRepository repository;

    private final PermissionTransform permissionTransform;

    private final PermissionCommandAdapter permissionCommandAdapter;

    @Override
    public Role saveRole(Role role) {
        return repository.save(role);
    }

    @Override
    public void addPermissionRole(Role role, RoleReq roleReq) {
        if(roleReq.getPermissionReq().isEmpty()) {
            return;
        }
        role.setPermissionList(null);
        List<Permission> permissions = permissionTransform.mappingPermissionList(roleReq.getPermissionReq());
        permissions.forEach(permission -> permission.setId(new PermissionId(permission.getId().getMenuId(), role)));
        role.setPermissionList(permissions);
    }
}
