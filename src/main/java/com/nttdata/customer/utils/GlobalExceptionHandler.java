package com.nttdata.customer.utils;

import org.openapitools.model.CustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomerResponse> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("Error de validación: {}", e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CustomerResponse()
                        .status(400)
                        .message(Constants.ERROR_VALIDATION_MESSAGE)
                        .customers(null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomerResponse> handleGeneralException(Exception e) {
        log.error("Error inesperado: ", e);
        return ResponseEntity
                .status(500)
                .body(new CustomerResponse()
                        .status(500)
                        .message(Constants.ERROR_INTERNAL)
                        .customers(null));
    }
}
