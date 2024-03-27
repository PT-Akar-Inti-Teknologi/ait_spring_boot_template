package org.ait.project.guideline.example.modules.role.dto.response;


import java.util.List;

import org.ait.project.guideline.example.modules.permission.dto.response.PermissionRes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class RoleRes {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("permissions")
    private List<PermissionRes> permissions;
    
}
