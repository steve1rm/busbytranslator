package me.androidbox.busbytranslator.android.translate.presentation.screens

import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.busbytranslator.android.translate.presentation.components.LanguageDropDown
import me.androidbox.busbytranslator.android.translate.presentation.components.RememberTextToSpeech
import me.androidbox.busbytranslator.android.translate.presentation.components.SwapLanguagesButton
import me.androidbox.busbytranslator.android.translate.presentation.components.TranslateTextField
import me.androidbox.busbytranslator.android.translate.presentation.components.rememberTextToSpeech
import me.androidbox.busbytranslator.translate.presentation.TranslateEvent
import me.androidbox.busbytranslator.translate.presentation.TranslateState
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
    translateState: TranslateState,
    onTranslateEvent: (TranslateEvent) -> Unit,
    modifier: Modifier = Modifier) {

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
                val context = LocalContext.current
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
