package org.ait.project.guideline.example.config.properties.part;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  private String secret;
  private Long accessTokenExpirationTime;
  private Long refreshTokenExpirationTime;
}
