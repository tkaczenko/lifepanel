package com.github.tkaczenko.lifepanel.api.handler

import org.springframework.core.Ordered

/**
 * @author Andrii Tkachenko
 */
interface ExceptionHandlerOrder {
    companion object {
        const val DEFAULT_EXCEPTION_ORDER = Ordered.LOWEST_PRECEDENCE
        const val NOT_FOUND_EXCEPTION_ORDER = DEFAULT_EXCEPTION_ORDER - 1
        const val VALIDATION_EXCEPTION_ORDER = NOT_FOUND_EXCEPTION_ORDER - 1
    }
}
