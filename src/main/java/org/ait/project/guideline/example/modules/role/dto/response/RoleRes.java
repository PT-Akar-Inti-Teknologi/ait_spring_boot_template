package org.ait.project.guideline.example.modules.role.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ait.project.guideline.example.modules.permission.dto.response.PermissionRes;

import java.util.List;

@Data
public class RoleRes {

    @JsonProperty("roleId")
    private Integer id;

    @JsonProperty("roleName")
    private String name;

    private List<PermissionRes> permission;
}
