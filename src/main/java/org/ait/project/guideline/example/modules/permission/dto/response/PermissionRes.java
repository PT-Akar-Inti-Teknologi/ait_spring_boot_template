package org.ait.project.guideline.example.modules.permission.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class PermissionRes {

  private String id;

  @JsonProperty("menu")
  private String menu;

}
