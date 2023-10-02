package org.ait.project.guideline.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties
public class ApplicationProperties {

    private RestClient restClient;

    public static class RestClient {
        private Url url;

        private static class Url {
            private String reqres;
        }
    }
}
