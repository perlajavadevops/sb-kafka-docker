package com.pgr.kafka.sb.orderseventsconsumer.domains;

import com.pgr.kafka.sb.orderseventsconsumer.enums.OrderStatusEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderEvent(

		Integer orderEventId,
		OrderStatusEnum orderStatusType, 
		@NotNull @Valid Order order

) {
}
