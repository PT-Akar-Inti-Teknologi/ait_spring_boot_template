package org.ait.project.guideline.example.modules.role.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.role.dto.request.RoleReq;
import org.ait.project.guideline.example.modules.role.dto.response.RoleRes;
import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.ait.project.guideline.example.modules.role.service.adapter.command.RoleCommandAdapter;
import org.ait.project.guideline.example.modules.role.service.adapter.query.RoleQueryAdapter;
import org.ait.project.guideline.example.modules.role.service.core.RoleCore;
import org.ait.project.guideline.example.modules.role.transform.RoleTransform;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleCoreImpl implements RoleCore {

    private final RoleCommandAdapter roleCommandAdapter;

    private final RoleQueryAdapter roleQueryAdapter;

    private final RoleTransform roleTransform;

    private final ResponseHelper responseHelper;
    @Override
    @Transactional
    public ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> addRole(RoleReq roleReq) {
        Role role = roleTransform.mappingToRole(roleReq);
        roleCommandAdapter.addPermissionRole(role, roleReq);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
                roleTransform.createRoleRes(roleCommandAdapter.saveRole(role)));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<RoleRes>>> getRoles(String startDate, String endDate, Pageable pageable) {
        Page<Role> rolePage = roleQueryAdapter.getRoleByStartDateAndEndDate(startDate, endDate, pageable);
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, rolePage,
                roleTransform.createRoleResList(rolePage.getContent()));
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> updateRole(Integer id, RoleReq roleReq) {
        Role role = roleQueryAdapter.findById(id);
        role.setName(roleReq.getName());
        roleCommandAdapter.addPermissionRole(role, roleReq);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
                roleTransform.createRoleRes(roleCommandAdapter.saveRole(role)));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> getRole(Integer id) {
        Role role = roleQueryAdapter.findById(id);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, roleTransform.createRoleRes(role));
    }
}
