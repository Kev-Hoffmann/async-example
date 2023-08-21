package com.example.asyncexample.error.dto;

import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDto {

  private String title;
  private Integer code;
  private ZonedDateTime timestamp;
  private String details;
}
