package org.ait.project.guideline.example.modules.permission.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author febrihasan
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginRes {
    private int id;
    private String name;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
}
