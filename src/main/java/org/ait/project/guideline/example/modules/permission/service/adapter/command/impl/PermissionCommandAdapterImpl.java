package org.ait.project.guideline.example.modules.permission.service.adapter.command.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.permission.model.entity.Permission;
import org.ait.project.guideline.example.modules.permission.model.repository.PermissionRepository;
import org.ait.project.guideline.example.modules.permission.service.adapter.command.PermissionCommandAdapter;
import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionCommandAdapterImpl implements PermissionCommandAdapter {

    private final PermissionRepository repository;

    @Override
    public Permission savePermission(Permission permission) {
        return repository.save(permission);
    }

    @Override
    public List<Permission> savePermissions(List<Permission> permissions) {
        return repository.saveAll(permissions);
    }

    @Override
    public void deletePermissionByRole(Role role) {
        repository.deleteByIdRole(role);
    }
}
