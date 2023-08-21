package com.example.asyncexample.config;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.asyncexample.error.ResourceNotFoundException;
import com.example.asyncexample.error.dto.ErrorMessageDto;
import java.time.ZonedDateTime;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(value = ResourceNotFoundException.class)
  public ErrorMessageDto resourceNotFoundException(
      ResourceNotFoundException resourceNotFoundException) {
    return ErrorMessageDto.builder()
        .title("Resource Not Found")
        .code(NOT_FOUND.value())
        .details(resourceNotFoundException.getLocalizedMessage())
        .timestamp(ZonedDateTime.now())
        .build();
  }
}
