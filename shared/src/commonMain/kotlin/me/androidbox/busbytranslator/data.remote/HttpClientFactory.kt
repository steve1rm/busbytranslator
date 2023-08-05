package me.androidbox.busbytranslator.data.remote

import io.ktor.client.HttpClient

expect class HttpClientFactory {
    fun create(): HttpClient
}