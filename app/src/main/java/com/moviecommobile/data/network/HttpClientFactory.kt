package com.moviecommobile.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) { //kodowanie i dekodowanie w formacie json
                json(
                    json = Json {
                        ignoreUnknownKeys = true // ignoruj nieznane klucze w odpowiedzi
                    }
                )
            }
            install(HttpTimeout) { // limity czasowe
                socketTimeoutMillis = 20_000L //czas na polaczenie z serverem w s
                requestTimeoutMillis = 20_000L //czas na odpowiedz od servera w s
            }
            install(Logging) { //logowanie zadan i odpowiedzi
                logger = object : Logger { //wlasna instancja loggera
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL //loggujemy wszytsko
            }
            defaultRequest { //domyslne ustawienia dla kazdego requestu
                contentType(ContentType.Application.Json) //domyslny format danych
            }
        }
    }
}