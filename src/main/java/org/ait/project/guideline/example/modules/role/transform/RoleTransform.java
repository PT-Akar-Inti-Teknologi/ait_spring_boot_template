package org.ait.project.guideline.example.modules.role.transform;

import org.ait.project.guideline.example.modules.permission.transform.PermissionTransform;
import org.ait.project.guideline.example.modules.role.dto.request.RoleReq;
import org.ait.project.guideline.example.modules.role.dto.response.RoleRes;
import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = PermissionTransform.class)
public interface RoleTransform {

    @Named("mappingRole")
    Role mappingToRole(RoleReq roleReq);

    @Named("createRoleRes")
    @Mapping(target = "permission", source = "permissionList", qualifiedByName = "createPermResList")
    RoleRes createRoleRes(Role role);

    @Named("createRoleResList")
    @IterableMapping(qualifiedByName = "createRoleRes")
    List<RoleRes> createRoleResList(List<Role> roleList);
}
