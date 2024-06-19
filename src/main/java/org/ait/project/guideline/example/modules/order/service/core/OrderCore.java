package org.ait.project.guideline.example.modules.order.service.core;

import org.ait.project.guideline.example.modules.order.dto.request.OrderReq;
import org.ait.project.guideline.example.modules.order.dto.response.OrderRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;


public interface OrderCore {

  ResponseEntity<ResponseTemplate<ResponseDetail<OrderRes>>> addOrder(OrderReq orderReq);

  ResponseEntity<ResponseTemplate<ResponseDetail<OrderRes>>> submitOrder(String id,
                                                                         OrderReq orderReq);

  ResponseEntity<ResponseTemplate<ResponseDetail<String>>> payOrder(String id, OrderReq orderReq);


}
