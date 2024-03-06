package org.ait.project.guideline.example.modules.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserRes {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

  /*  @JsonProperty("roles")
    private List<RoleRes> roles;*/

}
