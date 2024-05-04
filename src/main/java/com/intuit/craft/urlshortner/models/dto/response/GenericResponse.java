package com.intuit.craft.urlshortner.models.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record GenericResponse<TData>(TData data, boolean error) {

  public static <TData> ResponseEntity<GenericResponse<TData>> ok(TData data) {
    return new ResponseEntity<>(new GenericResponse<>(data, false), HttpStatus.OK);
  }

  public static <TData> ResponseEntity<GenericResponse<TData>> error(TData data) {
    return new ResponseEntity<>(new GenericResponse<>(data, true),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public static <TData> ResponseEntity<GenericResponse<TData>> error(TData data,
      HttpStatus httpStatus) {
    return new ResponseEntity<>(new GenericResponse<>(data, true), httpStatus);
  }

  public static <TData> ResponseEntity<GenericResponse<TData>> with(TData data,
      HttpStatus httpStatus) {
    return new ResponseEntity<>(new GenericResponse<>(data, false), httpStatus);
  }

  public static <TData> ResponseEntity<GenericResponse<TData>> with(TData data,
      HttpStatus httpStatus, boolean error) {
    return new ResponseEntity<>(new GenericResponse<>(data, error), httpStatus);
  }
}
