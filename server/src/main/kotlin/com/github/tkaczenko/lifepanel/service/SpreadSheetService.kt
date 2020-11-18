package com.github.tkaczenko.lifepanel.service

import com.github.tkaczenko.lifepanel.model.Month
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.*
import org.slf4j.Logger
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Andrii Tkachenko
 */
@Service
class SpreadsheetService(
    private val logger: Logger,
    private val sheets: Sheets,
    private val messageSource: MessageSource,
    private val utilityBillingReport: UtilityBillingReport
) {
    private val companion = utilityBillingReport.companion

    fun createSpreadsheet(city: String, locale: Locale): Spreadsheet {
        val mappedLocale = when (locale.language) {
            "ru" -> "RU"
            "en" -> "US"
            else -> "unknown"
        }
        val spreadsheet = Spreadsheet()
            .setProperties(
                SpreadsheetProperties()
                    .setTitle("Lifepanel")
                    .setLocale(locale.language + '_' + mappedLocale)
            )
            .setSheets(listOf(
                Sheet()
                    .setProperties(companion.sheetProperties.setTitle(city))
            ))
        return sheets.spreadsheets().create(spreadsheet).execute()
    }

    fun addHeader(spreadsheetId: String, city: String, locale: Locale) {
        val messages = IntRange(1, companion.NUM_OF_COLUMNS)
            .map { i -> messageSource.getMessage(String.format("label%d", i), null, locale) }
            .toList()
        val header = utilityBillingReport.header(messages)
        val updateSpreadsheetRequest = BatchUpdateSpreadsheetRequest()
            .setRequests(listOf(
                Request()
                    .setAppendCells(
                        AppendCellsRequest()
                            .setRows(listOf(RowData().setValues(header)))
                            .setFields("*")
                    ),
                Request()
                    .setAutoResizeDimensions(
                        AutoResizeDimensionsRequest()
                            .setDimensions(
                                DimensionRange()
                                    .setDimension("COLUMNS")
                                    .setStartIndex(0)
                                    .setEndIndex(companion.NUM_OF_COLUMNS)
                            )
                    )
            ))
        sheets.spreadsheets().batchUpdate(spreadsheetId, updateSpreadsheetRequest).execute()
    }

    fun addMonth(spreadsheetId: String, rowCount: Int, month: Month) {
        val monthHeader = utilityBillingReport.monthHeader(month)
        val rowsForMonth = getRowsForMonth(rowCount, month)
        val requests = BatchUpdateSpreadsheetRequest()
            .setRequests(listOf(
                Request()
                    .setAppendCells(
                        AppendCellsRequest()
                            .setFields("*")
                            .setRows(listOf(monthHeader))
                    ),
                Request()
                    .setAppendCells(
                        AppendCellsRequest()
                            .setFields("*")
                            .setRows(rowsForMonth)
                    ),
                Request()
                    .setMergeCells(
                        MergeCellsRequest()
                            .setMergeType("MERGE_ROWS")
                            .setRange(
                                GridRange()
                                    .setStartRowIndex(rowCount)
                                    .setEndRowIndex(rowCount + 1)
                                    .setStartColumnIndex(0)
                                    .setEndColumnIndex(companion.NUM_OF_COLUMNS)
                            )
                    ),
                Request()
                    .setAutoResizeDimensions(
                        AutoResizeDimensionsRequest()
                            .setDimensions(
                                DimensionRange()
                                    .setDimension("COLUMNS")
                                    .setStartIndex(0)
                                    .setEndIndex(companion.NUM_OF_COLUMNS)
                            )
                    )
            ))
        sheets.spreadsheets().batchUpdate(spreadsheetId, requests).execute()
    }

    private fun getRowsForMonth(rowCount: Int, month: Month): List<RowData> {
        var currentRow = rowCount + 2
        return month.invoices
            .map { invoice -> utilityBillingReport.rowDataForMonth(currentRow++, invoice) }
            .toList()
    }
}