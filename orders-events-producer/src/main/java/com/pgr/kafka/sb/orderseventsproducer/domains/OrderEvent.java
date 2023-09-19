package com.pgr.kafka.sb.orderseventsproducer.domains;

import com.pgr.kafka.sb.orderseventsproducer.enums.OrderStatusEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderEvent(

		Integer orderEventId,
		OrderStatusEnum orderStatusType, 
		@NotNull @Valid Order order

) {
}
