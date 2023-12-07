package org.ait.project.guideline.example.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.config.properties.part.RestClientProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class ApplicationProperties {

    private final RestClientProperties restClient;

}
