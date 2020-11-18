package com.github.tkaczenko.lifepanel.configuration

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import java.io.InputStreamReader
import java.security.GeneralSecurityException
import java.util.*

/**
 * @author Andrii Tkachenko
 */
@Configuration
class GoogleServicesConfig {
    private companion object {
        const val CREDENTIALS_FILE_PATH = "/client_id.json"
        const val TOKENS_DIRECTORY_PATH = "tokens"
        const val APPLICATION_NAME = "lifepanel"

        @JvmField
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS)
    }

    @Bean
    @Throws(IOException::class, GeneralSecurityException::class)
    fun sheets(): Sheets {
        // Build a new authorized API client service.
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        return Sheets.Builder(httpTransport, jsonFactory, getCredentials(httpTransport))
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        // Load client secrets.
        val input = ClassPathResource(CREDENTIALS_FILE_PATH).inputStream
        val clientSecrets = GoogleClientSecrets.load(jsonFactory, InputStreamReader(input))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, jsonFactory, clientSecrets, scopes)
            .setDataStoreFactory(FileDataStoreFactory(java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }
}