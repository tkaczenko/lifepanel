package com.github.tkaczenko.lifepanel.api.handler

import com.github.tkaczenko.lifepanel.exception.ObjectNotFoundException
import org.slf4j.Logger
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


/**
 * @author Andrii Tkachenko
 */
@ControllerAdvice(value = ["com.flexshore.installmentbroker.server.api.controller"])
@Order(ExceptionHandlerOrder.NOT_FOUND_EXCEPTION_ORDER)
class NotFoundExceptionHandler(
    private val logger: Logger
) {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [ObjectNotFoundException::class])
    fun defaultErrorHandler(e: Exception?) {
        logger.error("Object not found", e)
    }
}
