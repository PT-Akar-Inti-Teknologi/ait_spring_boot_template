package org.ait.project.guideline.example.modules.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
public class UserReq {

    @NotBlank(message = "{name.required}")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "{email.required}")
    @JsonProperty("email")
    private String email;


    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("rolesId")
    private List<Integer> rolesId;
}
