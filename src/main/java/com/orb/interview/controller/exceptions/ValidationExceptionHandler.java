package com.orb.interview.controller.exceptions;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionHandler.class);

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    LOGGER.warn("invalid request parameters", ex);
    BindingResult bindingResult = ex.getBindingResult();

    List<ValidationErrorResponse.Error> fieldErrors = bindingResult.getFieldErrors().stream()
            .map(fe -> new ValidationErrorResponse.Error(fe.getDefaultMessage(), fe.getField(), fe.getRejectedValue()))
            .collect(Collectors.toList());

    ValidationErrorResponse errors = new ValidationErrorResponse(fieldErrors);
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    LOGGER.warn("invalid request parameters", ex);
	  ValidationErrorResponse.Error error = new ValidationErrorResponse.Error(ex.getMessage(), ex.getParameterName(), null);
	  List<ValidationErrorResponse.Error> fieldErrors =
			Lists.newArrayList(error);
    ValidationErrorResponse errors = new ValidationErrorResponse(fieldErrors);
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

}

