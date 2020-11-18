package com.github.tkaczenko.lifepanel.api.model

import java.io.Serializable

/**
 * @author Andrii Tkachenko
 */
open class AbstractResponse(
    val error: Boolean = false,
    val errorMessage: String,
    val errorDetails: List<ErrorDetail> = emptyList()
) : Serializable