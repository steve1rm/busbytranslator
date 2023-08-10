package me.androidbox.busbytranslator.translate.domain.translate

import me.androidbox.busbytranslator.core.domain.language.Language
import me.androidbox.busbytranslator.core.domain.util.Resource
import me.androidbox.busbytranslator.translate.domain.history.HistoryDataSource
import me.androidbox.busbytranslator.translate.domain.history.HistoryItem

class TranslateUseCase(
    private val translationClient: TranslationClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun execute(fromLanguage: Language, fromText: String, toLanguage: Language): Resource {
        return try {
            val translatedText = translationClient.translate(
                fromLanguage = fromLanguage,
                fromText = fromText,
                toLanguage = toLanguage
            )

            historyDataSource.insertHistory(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.languageCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.languageCode,
                    toText = translatedText
                )
            )

            Resource.Success(translatedText)
        }
        catch (translationException: TranslationException) {
            translationException.printStackTrace()

            Resource.Error(translationException)
        }
    }
}