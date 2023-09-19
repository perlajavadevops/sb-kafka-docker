package com.pgr.kafka.sb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pgr.kafka.sb.domain.LibraryEvent;
import com.pgr.kafka.sb.enums.LibraryEventType;
import com.pgr.kafka.sb.producer.LibraryEventProducer;

import jakarta.validation.Valid;

@RestController
public class LibraryEventsController {

	@Autowired
	LibraryEventProducer libraryEventProducer;

	@PostMapping("/v1/libraryevent")
	public ResponseEntity<?> postLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent)
			throws JsonProcessingException {

		if (LibraryEventType.NEW != libraryEvent.libraryEventType()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only NEW event type is supported");
		}
		// invoke kafka producer
		// libraryEventProducer.sendLibraryEvent_Approach2(libraryEvent);
		libraryEventProducer.sendLibraryEvent(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}

}
