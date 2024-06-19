package org.ait.project.guideline.example.modules.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import org.ait.project.guideline.example.modules.role.dto.response.RoleRes;

@Data
public class UserRes {

  @JsonProperty("id")
  private String id;

  @JsonProperty("username")
  private String username;

  @JsonProperty("email")
  private String email;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("birth_date")
  private String birthDate;

  @JsonProperty("roles")
  private List<RoleRes> roleId;
  //private RoleRes roleId;

}
