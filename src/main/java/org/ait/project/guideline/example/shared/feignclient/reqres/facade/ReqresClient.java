package org.ait.project.guideline.example.shared.feignclient.reqres.facade;

import org.ait.project.guideline.example.config.openfeign.OpenFeignReqresConfig;
import org.ait.project.guideline.example.shared.feignclient.reqres.facade.fallback.ReqresClientFallback;
import org.ait.project.guideline.example.shared.feignclient.reqres.response.ReqresListUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(
    value = "reqres-client",
    url = "${restclient.url.reqres}",
    configuration = OpenFeignReqresConfig.class,
    fallbackFactory = ReqresClientFallback.class
)
public interface ReqresClient {

  @GetMapping("/users")
  Optional<ReqresListUserResponse> getListUser(@RequestParam(required = false) String page);

}
