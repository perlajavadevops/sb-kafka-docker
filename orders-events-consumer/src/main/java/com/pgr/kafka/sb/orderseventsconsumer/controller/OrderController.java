package com.pgr.kafka.sb.orderseventsconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pgr.kafka.sb.orderseventsconsumer.domains.OrderEvent;
import com.pgr.kafka.sb.orderseventsconsumer.enums.OrderStatusEnum;
import com.pgr.kafka.sb.orderseventsconsumer.producer.OrderEventProducer;

import jakarta.validation.Valid;

@RestController
public class OrderController {

	@Autowired
	OrderEventProducer orderEventProducer;

	@PostMapping("/v1/orderEvent")
	public ResponseEntity<?> postLibraryEvent(@RequestBody @Valid OrderEvent orderEvent)
			throws JsonProcessingException {

		if (OrderStatusEnum.NEW != orderEvent.orderStatusType()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only NEW event type is supported");
		}
		// invoke kafka producer
		// orderEventProducer.sendOrderEvent_Approach2(orderEvent);
		orderEventProducer.sendOrderEvent(orderEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderEvent);
	}
}
