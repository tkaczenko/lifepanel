package com.github.tkaczenko.lifepanel.api.handler

import com.github.tkaczenko.lifepanel.api.model.ErrorResponse
import org.slf4j.Logger
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author Andrii Tkachenko
 */
@ControllerAdvice(value = ["com.github.tkaczenko.lifepanel.api.controller"])
@Order(ExceptionHandlerOrder.DEFAULT_EXCEPTION_ORDER)
class DefaultExceptionHandler(
    private val logger: Logger
) {
    @ResponseBody
    @ResponseStatus
    @ExceptionHandler(value = [Exception::class])
    fun defaultErrorHandler(e: Exception): ErrorResponse {
        logger.error("Service error", e)
        return ErrorResponse(e.message)
    }
}

