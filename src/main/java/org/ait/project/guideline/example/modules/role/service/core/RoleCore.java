package org.ait.project.guideline.example.modules.role.service.core;

import org.ait.project.guideline.example.modules.role.dto.request.RoleReq;
import org.ait.project.guideline.example.modules.role.dto.response.RoleRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RoleCore {

    ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> addRole(RoleReq roleReq);

    ResponseEntity<ResponseTemplate<ResponseCollection<RoleRes>>> getRoles(String startDate, String endDate, Pageable pageable);

    ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> updateRole(Integer id, RoleReq roleReq);

    ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> getRole(Integer id);
}
