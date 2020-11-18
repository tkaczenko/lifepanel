package com.github.tkaczenko.lifepanel.api.model

/**
 * @author Andrii Tkachenko
 */
class ErrorResponse : AbstractResponse {
    constructor(errorMessage: String?) : super(true, errorMessage!!)
    constructor(errorMessage: String?, details: List<ErrorDetail>?) : super(true, errorMessage!!, details!!)
}
