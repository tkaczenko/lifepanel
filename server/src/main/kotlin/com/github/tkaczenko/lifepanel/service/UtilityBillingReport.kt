package com.github.tkaczenko.lifepanel.service

import com.github.tkaczenko.lifepanel.model.Invoice
import com.github.tkaczenko.lifepanel.model.Month
import com.github.tkaczenko.lifepanel.util.*
import com.google.api.services.sheets.v4.model.*
import org.springframework.stereotype.Component
import java.time.LocalDate

/**
 * @author Andrii Tkachenko
 */
@Component
class UtilityBillingReport {
    companion object {
        const val NUM_OF_COLUMNS = 19

        val sheetProperties: SheetProperties = SheetProperties()
            .setSheetId(0)
            .setSheetType("GRID")
            .setGridProperties(
                GridProperties()
                    .setRowCount(1)
                    .setColumnCount(NUM_OF_COLUMNS)
            )
        val border: Border = Border()
            .setStyle("SOLID")
            .setWidth(1)
            .setColor(
                Color()
                    .setRed(0F)
                    .setBlue(0F)
                    .setGreen(0F)
            )
    }

    var companion = Companion

    fun header(messages: List<String>): List<CellData> {
        return messages
            .map { message ->
                CellData()
                    .setUserEnteredValue(
                        ExtendedValue()
                            .setStringValue(message)
                    )
                    .setUserEnteredFormat(
                        CellFormat()
                            .setHorizontalAlignment("CENTER")
                            .setBorders(
                                Borders()
                                    .setBottom(border)
                                    .setLeft(border)
                                    .setRight(border)
                                    .setTop(border)
                            )
                            .setBackgroundColor(
                                Color()
                                    .setBlue(110F)
                                    .setGreen(110F)
                                    .setRed(110F)
                            )
                            .setTextFormat(
                                TextFormat()
                                    .setBold(true)
                            )
                    )
            }
            .toList()
    }

    fun monthHeader(month: Month): RowData? {
        return RowData()
            .setValues(listOf(
                CellData()
                    .setUserEnteredValue(
                        ExtendedValue()
                            .setStringValue(month.header)
                    )
                    .setUserEnteredFormat(
                        CellFormat()
                            .setHorizontalAlignment("LEFT")
                            .setTextFormat(
                                TextFormat()
                                    .setItalic(true)
                                    .setBold(true)
                            )
                    )
            ))
    }

    fun rowDataForMonth(rowNumber: Number, invoice: Invoice): RowData {
        return RowData()
            .setValues(listOf(
                stringCell(invoice.name, "LEFT"),
                dateCell(invoice.dateOfReading),
                stringCell(invoice.account, "RIGHT"),
                stringCell(invoice.numOfMeter, "CENTER"),
                stringCell(invoice.period, "CENTER"),
                numberCell(invoice.previous.toDouble(), "RIGHT"),
                numberCell(invoice.current.toDouble(), "RIGHT"),
                formulaCell(String.format("=G%d-F%d", rowNumber, rowNumber)),
                numberCell(invoice.rate, "RIGHT"),
                formulaCell(String.format("=ROUND(I%d; 2)", rowNumber)),
                numberCell(invoice.overpayment, "RIGHT"),
                formulaCell(String.format("=ROUND(H%d*I%d-K%d; 2)", rowNumber, rowNumber, rowNumber)),
                dateCell(LocalDate.now()),
                linkCell(invoice.checkLink),
                stringCell(invoice.phone, "CENTER"),
                stringCell(invoice.address, "LEFT"),
                stringCell(invoice.username, "CENTER"),
                stringCell(invoice.password, "CENTER"),
                linkCell(invoice.link)
            ))
    }
}