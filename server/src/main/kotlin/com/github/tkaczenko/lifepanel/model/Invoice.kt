package com.github.tkaczenko.lifepanel.model

import java.time.LocalDate

/**
 * @author Andrii Tkachenko
 */
data class Invoice(
    val name: String,
    val dateOfReading: LocalDate,
    val account: String,
    val numOfMeter: String,
    val period: String,
    val previous: Int,
    val current: Int,
    val rate: Double,
    val overpayment: Double,
    val dateOfPayment: LocalDate,
    val checkLink: String,
    val phone: String,
    val link: String,
    val username: String,
    val password: String,
    val address: String
)