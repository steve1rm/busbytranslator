package me.androidbox.busbytranslator.translate.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import me.androidbox.busbytranslator.core.domain.util.Resource
import me.androidbox.busbytranslator.core.domain.util.toCommonStateFlow
import me.androidbox.busbytranslator.core.presentation.UiLanguage
import me.androidbox.busbytranslator.translate.domain.history.HistoryDataSource
import me.androidbox.busbytranslator.translate.domain.translate.TranslateUseCase
import me.androidbox.busbytranslator.translate.domain.translate.TranslationException

class TranslationViewModel(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main.immediate)
    private var currentTranslationJob: Job? = null

    private val _translateState = MutableStateFlow(TranslateState())
    val translateState = combine(_translateState, historyDataSource.getHistory()) { translateState, historyState ->
        if(translateState.history != historyState) {
            translateState.copy(
                history = historyState.mapNotNull { historyItem ->
                    UiHistoryItem(
                        id = historyItem.id ?: return@mapNotNull null,
                        fromText = historyItem.fromText,
                        toText = historyItem.toText,
                        fromLanguage = UiLanguage.byCode(historyItem.fromLanguageCode),
                        toLanguage = UiLanguage.byCode(historyItem.toLanguageCode)
                    )
                }
            )
        }
        else {
            translateState
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        TranslateState()
    ).toCommonStateFlow()

    fun onTranslationEvent(translationEvent: TranslateEvent) {
        when(translationEvent) {
            is TranslateEvent.ChangeTranslationText -> {
                _translateState.update { translateState ->
                    translateState.copy(
                        fromText = translationEvent.text
                    )
                }
            }
            is TranslateEvent.ChooseFromLanguage -> {
                _translateState.update { translateState ->
                        translateState.copy(
                            isChoosingFromLanguage = false,
                            fromLanguage = translationEvent.language
                        )
                }
            }
            is TranslateEvent.ChooseToLanguage -> {
                val newTranslationState = _translateState.updateAndGet { translateState ->
                    translateState.copy(
                        isChoosingToLanguage = false,
                        toLanguage = translationEvent.language
                    )
                }
                translate(newTranslationState)
            }
            TranslateEvent.CloseTranslation -> {
                _translateState.update { translateState ->
                    translateState.copy(
                        isTranslating = false,
                        fromText = "",
                        toText = null
                    )
                }
            }
            TranslateEvent.EditTranslation -> {
                if(translateState.value.toText != null) {
                    _translateState.update { translateState ->
                        translateState.copy(
                            toText = null,
                            isTranslating = false
                        )
                    }
                }
            }
            TranslateEvent.OnErrorSeen -> {
                _translateState.update { translateState ->
                    translateState.copy(
                        error = null
                    )
                }
            }
            TranslateEvent.OpenFromLanguageDropDown -> {
                _translateState.update { translateState ->
                    translateState.copy(
                        isChoosingFromLanguage = true
                    )
                }
            }
            TranslateEvent.OpenToLanguageDropDown -> {
                _translateState.update { translateState ->
                    translateState.copy(
                        isChoosingToLanguage = true
                    )
                }
            }
            is TranslateEvent.SelectHistory -> {
                /** Cancel current translation so the history doesn't get updated
                  * after the current translation has completed */
                currentTranslationJob?.cancel()

                _translateState.update { translateState ->
                    translateState.copy(
                        fromText = translationEvent.item.fromText,
                        toText = translationEvent.item.toText,
                        isTranslating = false,
                        fromLanguage = translationEvent.item.fromLanguage,
                        toLanguage = translationEvent.item.toLanguage
                    )
                }
            }
            TranslateEvent.StopChoosingLanguage -> {
                _translateState.update { translateState ->
                    translateState.copy(
                        isChoosingFromLanguage = false,
                        isChoosingToLanguage = false
                    )
                }
            }
            is TranslateEvent.SubmitVoiceResult -> {
                _translateState.update { translateState ->
                    translateState.copy(
                        fromText = translationEvent.result.ifBlank { translateState.fromText },
                        isTranslating = if(translationEvent.result != null) false else translateState.isTranslating,
                        toText = if(translationEvent.result != null) null else translateState.toText
                    )
                }
            }
            TranslateEvent.SwapLanguages -> {
                _translateState.update { translateState ->
                    translateState.copy(
                        fromLanguage = translateState.toLanguage,
                        toLanguage = translateState.fromLanguage,
                        fromText = translateState.toText.orEmpty(),
                        toText = if(translateState.toText != null) translateState.fromText else null
                    )
                }
            }
            TranslateEvent.Translate -> {
                translate(translateState.value)
            }
            else -> {
                Unit
            }
        }
    }

    private fun translate(translateState: TranslateState) {
        if(!translateState.isTranslating || translateState.fromText.isNotBlank()) {
            currentTranslationJob = viewModelScope.launch {
                _translateState.update { translateState ->
                    translateState.copy(
                        isTranslating = true
                    )
                }
                val result = translateUseCase.execute(
                    fromLanguage = translateState.fromLanguage.language,
                    fromText = translateState.fromText,
                    toLanguage = translateState.toLanguage.language
                )

                when(result) {
                    is Resource.Success -> {
                        _translateState.update { translateState ->
                            translateState.copy(
                                isTranslating = false,
                                toText = result.data
                            )
                        }
                    }

                    is Resource.Error -> {
                        _translateState.update { translateState ->
                            translateState.copy(
                                isTranslating = false,
                                error = (result.throwable as? TranslationException)?.error)
                        }
                    }
                }
            }
        }
    }
}