package org.ait.project.guideline.example.modules.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import org.ait.project.guideline.example.modules.role.model.jpa.entity.Role;

@Data
public class UserReq {

    @JsonProperty("username")
    private String username;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    private String lastName;
    
    @JsonProperty("birthDate")
    private String birthDate;
    
    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("role")
    private Role role;
    
    @JsonProperty("searchBy")
    private String searchBy;
    
    @JsonProperty("sortBy")
    private String sortBy;
    
    @JsonProperty("sortField")
    private String sortField;
    
    @JsonProperty("pageNumber")
    private Integer pageNumber;
    
    @JsonProperty("pageSize")
    private Integer pageSize;
    
    
   
}
