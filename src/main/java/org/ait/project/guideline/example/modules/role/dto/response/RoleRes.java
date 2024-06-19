package org.ait.project.guideline.example.modules.role.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import org.ait.project.guideline.example.modules.permission.dto.response.PermissionRes;


@Data
public class RoleRes {

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("permissions")
  private List<PermissionRes> permissions;

}
