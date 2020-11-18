package com.github.tkaczenko.lifepanel.model

/**
 * @author Andrii Tkachenko
 */
data class Month(
    val header: String,
    val city: String,
    val invoices: List<Invoice>
)