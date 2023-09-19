package com.pgr.kafka.sb.domain;

import com.pgr.kafka.sb.enums.LibraryEventType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record LibraryEvent(Integer libraryEventId, LibraryEventType libraryEventType, @NotNull @Valid Book book

) {

}
