package com.darglk.exposedpoc.exception

import org.springframework.http.HttpStatus
import org.springframework.validation.Errors
import org.springframework.validation.FieldError

class ValidationException(private val errors: Errors) :
    CustomException("validation error", HttpStatus.BAD_REQUEST.value()) {
    override fun serializeErrors(): List<ErrorResponse> {
        return errors.fieldErrors
            .map { error: FieldError -> ErrorResponse(error.defaultMessage, error.field) }
    }
}