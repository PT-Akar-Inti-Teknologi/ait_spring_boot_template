package org.ait.project.guideline.example.modules.masterdata.dto.param;

import java.beans.ConstructorProperties;
import lombok.Data;

@Data
public class UserParam {

  private String search;

  @ConstructorProperties({"search"})
  public UserParam(String search) {
    this.search = search != null ? search : "";
  }

}
