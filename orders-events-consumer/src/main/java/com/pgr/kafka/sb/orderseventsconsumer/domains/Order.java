package com.pgr.kafka.sb.orderseventsconsumer.domains;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Order(@NotNull Integer orderId, @NotBlank String orderNumber, String[] items,
		@NotNull Double price, @NotNull Double taxRate, @NotNull Double totalPrice) {
}
