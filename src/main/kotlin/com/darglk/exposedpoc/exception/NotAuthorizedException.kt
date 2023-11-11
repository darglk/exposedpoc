package com.darglk.exposedpoc.exception

import com.darglk.exposedpoc.exception.CustomException
import com.darglk.exposedpoc.exception.ErrorResponse
import org.springframework.http.HttpStatus

class NotAuthorizedException : CustomException("Unauthorized", HttpStatus.UNAUTHORIZED.value()) {
    override fun serializeErrors(): List<ErrorResponse> {
        return listOf(ErrorResponse("Not authorized", null))
    }
}