package com.pgr.kafka.sb.orderseventsproducer.producer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgr.kafka.sb.orderseventsproducer.domains.OrderEvent;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderEventProducer {

	// @Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;

	@Value("${spring.kafka.topic}")
	public String topic;

	// @Autowired
	ObjectMapper objectMapper;

	public OrderEventProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public CompletableFuture<SendResult<Integer, String>> sendOrderEvent(@Valid OrderEvent orderEvent)
			throws JsonProcessingException {
		Integer key = orderEvent.orderEventId();
		String value = objectMapper.writeValueAsString(orderEvent);

		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);
		var completableFuture = kafkaTemplate.send(producerRecord);
		return completableFuture.whenComplete((sendResult, throwable) -> {
			if (throwable != null) {
				handleFailure(key, value, throwable);
			} else {
				handleSuccess(key, value, sendResult);
			}
		});

	}

	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

		List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));

		return new ProducerRecord<>(topic, null, key, value, recordHeaders);
	}

	private void handleFailure(Integer key, String value, Throwable ex) {
		log.error("Error Sending the Message and the exception is {}", ex.getMessage());
	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
		log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value,
				result.getRecordMetadata().partition());
	}

}
