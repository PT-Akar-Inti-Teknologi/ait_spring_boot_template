package org.ait.project.guideline.example.modules.order.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.order.dto.request.OrderReq;
import org.ait.project.guideline.example.modules.order.dto.response.OrderRes;
import org.ait.project.guideline.example.modules.order.service.core.OrderCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "API Order CRUD")
@RequestMapping("/order")
public class OrderController {
  private final OrderCore orderCore;


  @Operation(summary = "API to Order Product")
  @PostMapping("/add")
  public ResponseEntity<ResponseTemplate<ResponseDetail<OrderRes>>> addOrder(
      @Valid @RequestBody OrderReq orderReq) {
    return orderCore.addOrder(orderReq);
  }

  @Operation(summary = "API to submit order")
  @PutMapping("/submit/{id}")
  public ResponseEntity<ResponseTemplate<ResponseDetail<OrderRes>>> submitOrder(
      @PathVariable("id") String id, @Valid @RequestBody OrderReq orderReq) {
    return orderCore.submitOrder(id, orderReq);
  }

  @Operation(summary = "API to get pay order")
  @PostMapping("/pay/{id}")
  public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> payOrder(
      @PathVariable("id") String id, @RequestBody OrderReq orderReq) {
    return orderCore.payOrder(id, orderReq);
  }

}
