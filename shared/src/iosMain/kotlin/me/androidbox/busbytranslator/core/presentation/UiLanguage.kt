package me.androidbox.busbytranslator.core.presentation

import me.androidbox.busbytranslator.core.domain.language.Language

actual class UiLanguage(
    actual val language: Language,
    val imageName: String
) {
    actual companion object {
        actual fun byCode(languageCode: String): UiLanguage {
            return allLanguages.find { uiLanguage ->
                uiLanguage.language.languageCode == languageCode
            } ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }

        actual val allLanguages: List<UiLanguage>
            get() {
                return Language.entries.map { language ->
                    UiLanguage(
                        language = language,
                        imageName = language.languageName.lowercase()
                    )
                }
            }
    }
}