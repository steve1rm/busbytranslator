package me.androidbox.busbytranslator.translate.domain.translate

import me.androidbox.busbytranslator.core.domain.language.Language

interface TranslationClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}