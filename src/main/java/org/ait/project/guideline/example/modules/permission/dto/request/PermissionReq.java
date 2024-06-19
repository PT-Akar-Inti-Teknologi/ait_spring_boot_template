package org.ait.project.guideline.example.modules.permission.dto.request;

import lombok.Data;

@Data
public class PermissionReq {

  private Integer menuId;

  private String menuName;

  private String action;
}
