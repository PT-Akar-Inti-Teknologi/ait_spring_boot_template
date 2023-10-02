package org.ait.project.guideline.example.modules.permission.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class PermissionRes {

    private Integer id;
    @JsonProperty("role_id")
    private Integer roleId;

    @JsonProperty("menu_name")
    private String menuName;

    private String action;
}
