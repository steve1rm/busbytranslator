package me.androidbox.busbytranslator.data.translate

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import me.androidbox.busbytranslator.NetworkConstant
import me.androidbox.busbytranslator.core.domain.language.Language
import me.androidbox.busbytranslator.translate.domain.translate.TranslateError
import me.androidbox.busbytranslator.translate.domain.translate.TranslationClient
import me.androidbox.busbytranslator.translate.domain.translate.TranslationException

class KtorTranslationClient(
    private val httpClient: HttpClient
) : TranslationClient {

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        val result = try {
            httpClient.post {
                url(NetworkConstant.BASE_URL + "/translate")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDto(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromLanguage.languageCode,
                        targetLanguageCode = toLanguage.languageCode
                    )
                )
            }
        }
        catch (exception: IOException) {
            throw TranslationException(TranslateError.SERVICE_UNAVAILABLE)
        }

        when (result.status.value) {
            in 200..299 -> Unit
            in 400 .. 499 -> throw TranslationException(TranslateError.CLIENT_ERROR)
            500 -> throw TranslationException(TranslateError.SERVER_ERROR)
            else -> throw TranslationException(TranslateError.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        }
        catch(exception: Exception) {
            throw TranslationException(TranslateError.SERVER_ERROR)
        }
    }
}