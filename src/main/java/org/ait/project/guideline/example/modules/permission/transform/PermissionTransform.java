package org.ait.project.guideline.example.modules.permission.transform;

import org.ait.project.guideline.example.modules.permission.dto.request.PermissionReq;
import org.ait.project.guideline.example.modules.permission.dto.response.PermissionRes;
import org.ait.project.guideline.example.modules.permission.model.entity.Permission;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionTransform {

    @Named("mappingPermission")
    @Mapping(target = "id.menuId", source = "menuId")
    Permission mappingPermission(PermissionReq permissionReq);

    @Named("mappingPermissionList")
    @IterableMapping(qualifiedByName = "mappingPermission")
    List<Permission> mappingPermissionList(List<PermissionReq> permissionReq);

    @Named("createPermRes")
    @Mapping(target = "id", source = "permission.id.menuId")
    @Mapping(target = "roleId", source = "permission.id.role.id")
    PermissionRes createPermRes(Permission permission);

    @Named("createPermResList")
    @IterableMapping(qualifiedByName = "createPermRes")
    List<PermissionRes> createPermResList(List<Permission> permissions);


}
