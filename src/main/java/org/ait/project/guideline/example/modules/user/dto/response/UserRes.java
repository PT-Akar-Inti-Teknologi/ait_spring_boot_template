package org.ait.project.guideline.example.modules.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

import org.ait.project.guideline.example.modules.role.dto.response.RoleRes;

@Data
public class UserRes {

    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    private String lastName;
    
    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("roles")
    private List<RoleRes> roleId;
    //private RoleRes roleId;

}
