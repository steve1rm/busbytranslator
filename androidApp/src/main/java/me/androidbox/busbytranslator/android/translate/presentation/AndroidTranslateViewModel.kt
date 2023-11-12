package me.androidbox.busbytranslator.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import me.androidbox.busbytranslator.translate.domain.history.HistoryDataSource
import me.androidbox.busbytranslator.translate.domain.translate.TranslateUseCase
import me.androidbox.busbytranslator.translate.presentation.TranslateEvent
import me.androidbox.busbytranslator.translate.presentation.TranslationViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource
): ViewModel() {
    private val viewModel by lazy {
        TranslationViewModel(
            translateUseCase = translateUseCase,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val translationState = viewModel.translateState

    fun onTranslation(translationEvent: TranslateEvent) {
        viewModel.onTranslationEvent(translationEvent)
    }
}