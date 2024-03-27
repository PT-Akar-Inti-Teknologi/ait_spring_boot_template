package org.ait.project.guideline.example.shared.feignclient.reqres.facade.fallback;

import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.shared.feignclient.reqres.facade.ReqresClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ReqresClientFallback implements FallbackFactory<ReqresClient> {

  @Override
  public ReqresClient create(Throwable cause) {
    /*return request -> {
      log.error(cause.getMessage());
      return Optional.empty();
    };
  }*/
	  return null;
  }
}
