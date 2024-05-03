package com.intuit.craft.urlshortner.exceptions.handler;

import com.intuit.craft.urlshortner.exceptions.FatalException;
import com.intuit.craft.urlshortner.exceptions.ServiceException;
import com.intuit.craft.urlshortner.models.dto.ErrorResponseDto;
import com.intuit.craft.urlshortner.models.dto.GenericResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

@Log4j2
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<?> handleServiceException(ServiceException exception) {
    log.error("[SERVICE] Error encountered with message: {}, info: {}, exception: {}",
        exception.getMessage(), gatherInfo(exception.getInfo()), exception.gatherStackTrace());
    return GenericResponse.error(
        new ErrorResponseDto(false, exception.getMessage()),
        exception.getHttpStatus());
  }

  @ExceptionHandler(FatalException.class)
  public ResponseEntity<?> handleFatalException(FatalException exception) {
    log.error("[FATAL] Error encountered with message: {}, info: {}, exception: {}",
        exception.getMessage(), gatherInfo(exception.getInfo()), exception.gatherStackTrace());

    // TODO report to sentry

    return GenericResponse.error(
        new ErrorResponseDto(false, exception.getMessage()),
        exception.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentViolationException(
      MethodArgumentNotValidException exception) {

    log.error("[FATAL] Error encountered with message: {}, info: {}, exception: {}",
        exception.getMessage(), exception.getCause(), exception.getStackTrace());

    // TODO report to sentry
    String message = exception
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));

    return GenericResponse.error(new ErrorResponseDto(false, message),
        HttpStatus.BAD_REQUEST);
  }


  private String gatherInfo(String... info) {
    if (ArrayUtils.isEmpty(info)) {
      return "None";
    }
    return StringUtils.join(Arrays.asList(info), ", ");
  }

}
