package me.androidbox.busbytranslator.translate.domain.translate

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslationException(error: TranslateError)
    : Exception("An error has occurred when translating: $error")
