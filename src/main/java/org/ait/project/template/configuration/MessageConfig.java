package org.ait.project.template.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages/validation/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean(name = "responseMessageSource")
  public MessageSource responseMessageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages/core/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
