package org.ait.project.guideline.example.shared.feignclient.reqres.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.config.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReqresInterceptor implements RequestInterceptor {

    // get data intercept from applicationProperties
    private final ApplicationProperties applicationProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //TODO set your intercept request here
        requestTemplate.header("set-your-header","your-value");
    }
}
