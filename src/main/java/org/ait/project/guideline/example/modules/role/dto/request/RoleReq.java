package org.ait.project.guideline.example.modules.role.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ait.project.guideline.example.modules.permission.dto.request.PermissionReq;

import java.util.List;

@Data
public class RoleReq {

    @JsonProperty("name")
    private String name;

    @JsonProperty("permission")
    private List<PermissionReq> permissionReq;


}
