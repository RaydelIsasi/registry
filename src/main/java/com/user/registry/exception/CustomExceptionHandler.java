package com.user.registry.exception;

import com.user.registry.pojo.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {BadCredentialsException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleBadCredentialsException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "mensaje:" + ex.getMessage();
        return new ResponseEntity<Object>(new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(value
            = {Exception.class})
    @ResponseBody
    protected ResponseEntity<Object> handleException(
            RuntimeException ex, WebRequest request) {

        return new ResponseEntity<Object>(new ErrorResponse(ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value
            = {ConstraintViolationException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleDataViolationException(
            RuntimeException ex, WebRequest request) {


        return new ResponseEntity<Object>(new ErrorResponse(ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value
            = {TransactionSystemException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleTransactionSystemException(
            RuntimeException ex, WebRequest request) {

        String message = "";
        Throwable cause = ex.getCause();
        Throwable resultCause = null;
        while ((cause = cause.getCause()) != null && resultCause != cause) {
            resultCause = cause;
        }
        if (resultCause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolationsSet = ((ConstraintViolationException) resultCause).getConstraintViolations();
            if (constraintViolationsSet != null) {
                message = constraintViolationsSet.stream().map(
                        cv -> {
                            if (cv != null) {
                                return cv.getMessage();
                            }
                            return null;
                        }).collect(Collectors.joining(" and "));

            }

        }
        return new ResponseEntity<Object>(new ErrorResponse(message), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value
            = {CustomException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleCustomException(
            RuntimeException ex, WebRequest request) {

        return new ResponseEntity<Object>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
