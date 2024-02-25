package org.ronald.mc.service.fleet.rest;

import org.ronald.mc.service.fleet.generated.dto.ApiError;
import org.ronald.mc.service.fleet.service.exceptions.DuplicateEntityException;
import org.ronald.mc.service.fleet.service.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class FleetInventoryControllerAdvice {
    @ExceptionHandler(value = {DuplicateEntityException.class})
    public ResponseEntity<ApiError> duplicateEntityException(DuplicateEntityException ex, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setException("DUPLICATE");
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ApiError> resourceNotFoundException(NotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setException("NOT_FOUND");
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
