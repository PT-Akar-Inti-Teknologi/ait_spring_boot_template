package org.ait.project.guideline.example.modules.role.service.adapter.command;
import org.ait.project.guideline.example.modules.role.dto.request.RoleReq;
import org.ait.project.guideline.example.modules.role.model.entity.Role;

public interface RoleCommandAdapter {

    Role saveRole(Role role);

    void addPermissionRole(Role role, RoleReq roleReq);

}
