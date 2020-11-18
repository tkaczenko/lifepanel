@file:JvmName("SpreadsheetUtil")

package com.github.tkaczenko.lifepanel.util

import com.google.api.services.sheets.v4.model.CellData
import com.google.api.services.sheets.v4.model.CellFormat
import com.google.api.services.sheets.v4.model.ExtendedValue
import com.google.api.services.sheets.v4.model.NumberFormat
import java.time.LocalDate
import java.time.ZoneId

fun dateCell(date: LocalDate): CellData? {
    val milliseconds = date.atStartOfDay(ZoneId.of("Europe/Kyiv")).toInstant().toEpochMilli()
    return CellData()
        .setUserEnteredValue(
            ExtendedValue()
                .setNumberValue(Double.fromBits(milliseconds))
        )
        .setUserEnteredFormat(
            CellFormat()
                .setNumberFormat(
                    NumberFormat()
                        .setType("DATE")
                )
        )
}

fun linkCell(link: String): CellData? {
    val formulaForLink = String.format("=HYPERLINK(\"%s\"; \"%s\")", link, link)
    return CellData()
        .setUserEnteredValue(
            ExtendedValue()
                .setFormulaValue(formulaForLink)
        )
}

fun stringCell(str: String, alignment: String): CellData? {
    return CellData()
        .setUserEnteredValue(
            ExtendedValue()
                .setStringValue(str)
        )
        .setUserEnteredFormat(
            CellFormat()
                .setHorizontalAlignment(alignment)
        )
}

fun formulaCell(formula: String): CellData? {
    return CellData()
        .setUserEnteredValue(
            ExtendedValue()
                .setFormulaValue(formula)
        )
}

fun numberCell(value: Double, alignment: String): CellData? {
    return CellData()
        .setUserEnteredValue(
            ExtendedValue()
                .setNumberValue(value)
        )
        .setUserEnteredFormat(
            CellFormat()
                .setHorizontalAlignment(alignment)
        )
}