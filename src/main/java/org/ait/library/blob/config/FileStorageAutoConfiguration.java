package org.ait.library.blob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FileStorageAutoConfiguration {

  @Bean
  @ConditionalOnClass(GlobalConfig.class)
  public GlobalConfig globalConfig() {
    log.info("GlobalConfig class Created");
    return new GlobalConfig();
  }
}
