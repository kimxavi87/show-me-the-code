package com.kimxavi87.spring.conf.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        System.out.println(e.getLocalizedMessage());
        String substring = e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf(": ") + 2);
        String message = messageSource.getMessage(substring, null, Locale.KOREA);
        return handleExceptionInternal(e, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
