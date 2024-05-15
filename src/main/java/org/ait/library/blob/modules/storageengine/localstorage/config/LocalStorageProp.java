package org.ait.library.blob.modules.storageengine.localstorage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "localstorage")
public class LocalStorageProp {

  private String filePath;

  private boolean accessPublic;

  private String publicBaseUrl;
}
