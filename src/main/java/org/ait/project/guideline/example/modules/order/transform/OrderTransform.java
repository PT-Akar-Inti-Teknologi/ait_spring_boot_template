package org.ait.project.guideline.example.modules.order.transform;

import org.ait.project.guideline.example.modules.order.dto.request.OrderReq;
import org.ait.project.guideline.example.modules.order.dto.response.OrderRes;
import org.ait.project.guideline.example.modules.order.model.jpa.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface OrderTransform {

  @Named("createOrderResponse")
  OrderRes createOrderResponse(Order order);

  @Named("mappingOrder")
  Order mappingOrderFromOrderReq(OrderReq orderReq);

}
