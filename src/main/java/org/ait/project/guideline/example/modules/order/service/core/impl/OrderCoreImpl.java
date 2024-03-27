package org.ait.project.guideline.example.modules.order.service.core.impl;

import lombok.RequiredArgsConstructor;

import org.ait.project.guideline.example.modules.order.dto.request.OrderReq;
import org.ait.project.guideline.example.modules.order.dto.response.OrderRes;
import org.ait.project.guideline.example.modules.order.model.jpa.entity.Order;
import org.ait.project.guideline.example.modules.order.service.adapter.command.OrderCommandAdapter;
import org.ait.project.guideline.example.modules.order.service.core.OrderCore;
import org.ait.project.guideline.example.modules.order.transform.OrderTransform;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCoreImpl implements OrderCore {

	private final OrderTransform orderTransform;
	
	private final OrderCommandAdapter orderCommandAdapter;
	
	private final ResponseHelper responseHelper;
    
    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<OrderRes>>> addOrder(OrderReq orderReq) {
    	Order order = orderTransform.mappingOrderFromOrderReq(orderReq);
        Order addOrder = orderCommandAdapter.addOrder(order);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, orderTransform.createOrderResponse(addOrder));
    }
    
    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<OrderRes>>> submitOrder(String id, OrderReq orderReq) {
    	Order order = orderTransform.mappingOrderFromOrderReq(orderReq);
    	Order addOrder = orderCommandAdapter.submitOrder(id, orderReq.getStatus());
    	order.setStatus(addOrder.getStatus());
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, orderTransform.createOrderResponse(order));
    }
    
    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> payOrder(String id, OrderReq orderReq) {
    	return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, "Success");
    }
    
}
