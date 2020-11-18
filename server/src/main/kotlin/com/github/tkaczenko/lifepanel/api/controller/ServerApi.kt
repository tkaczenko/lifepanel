package com.github.tkaczenko.lifepanel.api.controller

import com.github.tkaczenko.lifepanel.service.SpreadsheetService
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Andrii Tkachenko
 */
@Validated
@RestController
@RequestMapping(path = ["/server/v1.0"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
class ServerApi(
    private val spreadsheetService: SpreadsheetService
) {
    @PostMapping()
    fun create() {

    }
}