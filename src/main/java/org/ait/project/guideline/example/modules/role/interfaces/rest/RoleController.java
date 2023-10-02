package org.ait.project.guideline.example.modules.role.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.role.dto.request.RoleReq;
import org.ait.project.guideline.example.modules.role.dto.response.RoleRes;
import org.ait.project.guideline.example.modules.role.service.core.RoleCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController implements RoleCore {

    private final RoleCore roleCore;

    @Override
    @PostMapping
    public ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> addRole(@RequestBody RoleReq roleReq) {
        return roleCore.addRole(roleReq);
    }

    @Override
    @GetMapping
    public ResponseEntity<ResponseTemplate<ResponseCollection<RoleRes>>> getRoles(
            @RequestParam(value = "start-date", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "end-date", required = false, defaultValue = "") String endDate,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 5) Pageable pageable
    ) {
        return roleCore.getRoles(startDate, endDate, pageable);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> updateRole(@PathVariable("id") Integer id,@RequestBody RoleReq roleReq) {
        return roleCore.updateRole(id, roleReq);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<RoleRes>>> getRole(@PathVariable("id") Integer id) {
        return roleCore.getRole(id);
    }
}
