package com.example.demo.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author [yun]
 */
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_ERROR = "Unexpected error occurred.";

    @ExceptionHandler(value = {BadRequestException.class, ConstraintViolationException.class})
    protected ResponseEntity<Object> badRequestHandler(final RuntimeException ex, final WebRequest webRequest) throws Exception {
        logger.error(ex);
        return handleException(new ErrorResponseException(HttpStatus.BAD_REQUEST, ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage()), ex), webRequest);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> internalErrorHandler(final RuntimeException ex, final WebRequest webRequest) throws Exception {
        logger.error(ex);
        return handleException(new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR), ex), webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(ex);
        final String errors = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return handleExceptionInternal(new ErrorResponseException(HttpStatus.BAD_REQUEST, ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors), ex),
                null, headers, status, request);
    }
}
