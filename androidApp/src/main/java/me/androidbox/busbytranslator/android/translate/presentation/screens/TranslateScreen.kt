package me.androidbox.busbytranslator.android.translate.presentation.screens

import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.busbytranslator.android.R
import me.androidbox.busbytranslator.android.translate.presentation.components.LanguageDropDown
import me.androidbox.busbytranslator.android.translate.presentation.components.SwapLanguagesButton
import me.androidbox.busbytranslator.android.translate.presentation.components.TranslateTextField
import me.androidbox.busbytranslator.android.translate.presentation.components.TranslationHistoryItem
import me.androidbox.busbytranslator.android.translate.presentation.components.rememberTextToSpeech
import me.androidbox.busbytranslator.translate.domain.translate.TranslateError
import me.androidbox.busbytranslator.translate.presentation.TranslateEvent
import me.androidbox.busbytranslator.translate.presentation.TranslateState
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
    translateState: TranslateState,
    onTranslateEvent: (TranslateEvent) -> Unit,
    modifier: Modifier = Modifier) {

    val context = LocalContext.current
    
    LaunchedEffect(key1 = translateState.error) {
        when(translateState.error) {
            TranslateError.SERVICE_UNAVAILABLE -> context.getString(R.string.error_service_unavailable)
            TranslateError.CLIENT_ERROR -> context.getString(R.string.error_client_error)
            TranslateError.SERVER_ERROR -> context.getString(R.string.error_server_error)
            TranslateError.UNKNOWN_ERROR -> context.getString(R.string.error_unknown_error)
            null -> null
        }?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
    
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    LanguageDropDown(
                        modifier = Modifier.align(Alignment.CenterStart),
                        uiLanguage = translateState.fromLanguage,
                        isOpen = translateState.isChoosingFromLanguage,
                        onClick = {
                            onTranslateEvent(TranslateEvent.OpenFromLanguageDropDown)
                        },
                        onDismiss = {
                            onTranslateEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectedLanguage = { uiLanguage ->
                            onTranslateEvent(TranslateEvent.ChooseFromLanguage(uiLanguage))
                        }
                    )

                    SwapLanguagesButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = {
                            onTranslateEvent(TranslateEvent.SwapLanguages)
                        })

                    LanguageDropDown(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        uiLanguage = translateState.toLanguage,
                        isOpen = translateState.isChoosingToLanguage,
                        onClick = {
                            onTranslateEvent(TranslateEvent.OpenToLanguageDropDown)
                        },
                        onDismiss = {
                            onTranslateEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectedLanguage = { uiLanguage ->
                            onTranslateEvent(TranslateEvent.ChooseToLanguage(uiLanguage))
                        }
                    )
                }
            }
            
            item {
                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                val rememberTextToSpeech = rememberTextToSpeech()

                TranslateTextField(
                    fromText = translateState.fromText,
                    toText = translateState.toText,
                    isTranslating = translateState.isTranslating,
                    fromLanguage = translateState.fromLanguage,
                    toLanguage = translateState.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onTranslateEvent(TranslateEvent.Translate)
                    },
                    onTextChange = { text ->
                        onTranslateEvent(TranslateEvent.ChangeTranslationText(text))
                    },
                    onCopyClick = { text ->
                        clipboardManager.setText(
                            buildAnnotatedString {
                                append(text)
                            })
                        Toast.makeText(
                            context,
                            "Has Copied $text",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onCloseClick = {
                        onTranslateEvent(TranslateEvent.CloseTranslation)
                    },
                    onSpeakerClick = {
                        rememberTextToSpeech.language = translateState.toLanguage.toLocale() ?: Locale.getDefault()

                        rememberTextToSpeech.speak(
                            translateState.toText,
                            QUEUE_FLUSH,
                            null,
                            null
                        )
                    },
                    onTextFieldClick = {
                        onTranslateEvent(TranslateEvent.EditTranslation)
                    },
                    modifier = Modifier.fillMaxWidth())
            }

            item {
                if(translateState.history.isNotEmpty()) {
                    Text(text = stringResource(R.string.history),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onPrimary)
                }
            }

            items(
                items = translateState.history,
                key = {
                    it.id
                }
            ) { historyItem ->
                TranslationHistoryItem(
                    historyItem = historyItem,
                    onClick = { uiHistoryItem ->
                        onTranslateEvent(TranslateEvent.SelectHistory(uiHistoryItem))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTranslateScreen() {
    MaterialTheme {
        TranslateScreen(
            translateState = TranslateState(),
            onTranslateEvent = {}
        )
    }
}
