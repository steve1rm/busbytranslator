package me.androidbox.busbytranslator.core.presentation

import me.androidbox.busbytranslator.core.domain.language.Language

expect class UiLanguage {
    val language: Language

    companion object {
        val allLanguages: List<UiLanguage>

        fun byCode(languageCode: String): UiLanguage
    }
}