package org.ait.project.guideline.example.config.properties;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class ApplicationProperties {

    private final RestClientProperties restClient;

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "restclient")
    public static class RestClientProperties {

        private Url url;

        @Data
        public class Url{
            private String reqres;
        }
    }
}
