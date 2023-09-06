/*
 * Copyright (c) 2020. All Rights Reserved. BO1 AG
 * Confidential: Restricted Internal Distribution
 */
package com.tn.app.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.tn.app.exception.ErrorCode.INVALID_USER_DATA;
import static com.tn.app.exception.ErrorCode.LARGE_REQUEST;
import static com.tn.app.exception.ErrorCode.MALFORMED_REQUEST;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CREATE_USER_URI = "uri=/api/users;";

    @ExceptionHandler(MultipartException.class)
    protected ResponseEntity<ResponseError> fileSizeLimit(final MultipartException e) {
        ResponseError responseError = getResponseError(LARGE_REQUEST);
        responseError.setDescription(e.getMessage());
        return ResponseEntity.status(PAYLOAD_TOO_LARGE).body(responseError);
    }

    @ExceptionHandler(RestApiReportedException.class)
    protected ResponseEntity<ResponseError> handleRestApiExtendedException(final RestApiReportedException ex) {
        final ResponseError apiError = getResponseError(ex.getError());
        apiError.setMessages(ex.getMessages());
        if (apiError.getMessages() == null)
            apiError.setMessages(List.of(Map.of(ex.getError().getTitle(), ex.getError().getMessage())));
        return ResponseEntity.status(ex.getError().getHttpStatus()).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ServletWebRequest swr = (ServletWebRequest) request;
        if (swr.getHttpMethod() == POST) {
            if (swr.getDescription(true).contains(CREATE_USER_URI))
                return getObjectResponseEntity(INVALID_USER_DATA, errors);
        }

        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ResponseError error = getResponseError(MALFORMED_REQUEST);
        error.setDescription(ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ResponseError error = getResponseError(MALFORMED_REQUEST);
        error.setDescription(ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    private ResponseEntity<Object> getObjectResponseEntity(ErrorCode errorCode, Map<String, String> errors) {
        ResponseError responseError = getResponseError(errorCode);
        responseError.setMessages(new LinkedList<>());
        splitEachErrorIntoSingleKeyValueMap(errors, responseError.getMessages());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(responseError);
    }

    private ResponseError getResponseError(ErrorCode error) {
        return ResponseError.builder()
                .code(error.getCode())
                .title(error.getTitle())
                .description(error.getMessage())
                .display(error.getDisplay())
                .build();
    }

    private void splitEachErrorIntoSingleKeyValueMap(Map<String, String> errors, List<Object> messages) {
        errors.forEach((k ,v) -> messages.add(Map.of(k,v)));
    }

}