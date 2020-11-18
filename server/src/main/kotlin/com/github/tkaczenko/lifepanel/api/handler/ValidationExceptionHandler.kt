package com.github.tkaczenko.lifepanel.api.handler

import com.github.tkaczenko.lifepanel.api.model.ErrorDetail
import com.github.tkaczenko.lifepanel.api.model.ErrorResponse
import org.slf4j.Logger
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.stream.Collectors

/**
 * @author Andrii Tkachenko
 */
@ControllerAdvice(value = ["com.github.tkaczenko.lifepanel.api.controller"])
@Order(ExceptionHandlerOrder.VALIDATION_EXCEPTION_ORDER)
class ValidationExceptionHandler(
    private val logger: Logger
) {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun defaultErrorHandler(e: MethodArgumentNotValidException): ErrorResponse {
        logger.error("Validation error", e)
        val result = e.bindingResult
        val errorDetails = result.fieldErrors
            .stream()
            .map<ErrorDetail> { v: FieldError -> ErrorDetail(v.field, v.defaultMessage) }
            .collect(Collectors.toList())
        return ErrorResponse("ValidationError", errorDetails)
    }
}
