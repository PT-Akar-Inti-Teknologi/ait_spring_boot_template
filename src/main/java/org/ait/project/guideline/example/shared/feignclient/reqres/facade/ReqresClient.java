package org.ait.project.guideline.example.shared.feignclient.reqres.facade;

import org.ait.project.guideline.example.shared.feignclient.reqres.dto.response.ReqresListUserResponse;
import org.ait.project.guideline.example.shared.feignclient.reqres.facade.fallback.ReqresClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(
        value = "reqres-client",
        url = "${restclient.url.reqres}",
        fallbackFactory = ReqresClientFallback.class
)
public interface ReqresClient {

    @GetMapping("/users")
    Optional<ReqresListUserResponse> getListUser(@RequestParam(required = false) String page);

}
