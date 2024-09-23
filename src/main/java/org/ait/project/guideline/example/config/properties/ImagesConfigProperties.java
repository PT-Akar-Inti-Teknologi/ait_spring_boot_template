package org.ait.project.guideline.example.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "image")
public class ImagesConfigProperties {

  private long maxSizeInMegaByte;

}