package com.github.tkaczenko.lifepanel

import com.github.tkaczenko.lifepanel.model.Invoice
import com.github.tkaczenko.lifepanel.model.Month
import com.github.tkaczenko.lifepanel.service.SpreadsheetService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.time.LocalDate
import java.util.*

/**
 * @author Andrii Tkachenko
 */
@SpringBootApplication
class ServerApp(private val spreadsheetService: SpreadsheetService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val locale = Locale("ru")
        val city = "Mykolaiv"
        val month = Month(
            city = city,
            header = "Month 2000",
            invoices = listOf(
                Invoice(name = "Name 1",
                    dateOfReading = LocalDate.now(),
                    account = "11111",
                    numOfMeter = "1212",
                    period = "03.19-03.19",
                    previous = 100,
                    current = 120,
                    address = "",
                    dateOfPayment = LocalDate.now(),
                    checkLink = "",
                    link = "",
                    overpayment = 0.0,
                    password = "",
                    username = "",
                    phone = "",
                    rate = 0.0),
                Invoice(name = "Name 2",
                    dateOfReading = LocalDate.now(),
                    account = "11111",
                    numOfMeter = "1212",
                    period = "03.19-03.19",
                    previous = 100,
                    current = 120,
                    address = "",
                    dateOfPayment = LocalDate.now(),
                    checkLink = "",
                    link = "",
                    overpayment = 0.0,
                    password = "",
                    username = "",
                    phone = "",
                    rate = 0.0)
            )
        )
        val spreadsheet = spreadsheetService.createSpreadsheet(city, locale)
        spreadsheetService.addHeader(spreadsheet.spreadsheetId, city, locale)
        spreadsheetService.addMonth(spreadsheet.spreadsheetId, spreadsheet.sheets[0].properties.gridProperties.rowCount, month)
        spreadsheetService.addMonth(spreadsheet.spreadsheetId, spreadsheet.sheets[0].properties.gridProperties.rowCount, month)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ServerApp::class.java, *args)
}