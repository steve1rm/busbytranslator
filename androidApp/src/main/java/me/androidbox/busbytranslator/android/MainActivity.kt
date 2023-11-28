package me.androidbox.busbytranslator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import me.androidbox.busbytranslator.android.core.presentation.Routes
import me.androidbox.busbytranslator.android.translate.presentation.AndroidTranslateViewModel
import me.androidbox.busbytranslator.android.translate.presentation.screens.TranslateScreen
import me.androidbox.busbytranslator.translate.presentation.TranslateEvent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TranslateRoot()
                }
            }
        }
    }
}

@Composable
fun TranslateRoot() {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Routes.TRANSLATE
    ) {
        composable(route = Routes.TRANSLATE) {
            val translationViewModel: AndroidTranslateViewModel = hiltViewModel()
            val translateState by translationViewModel.translationState.collectAsState()

            TranslateScreen(
                translateState = translateState,
                onTranslateEvent = { translateEvent ->
                    when(translateEvent) {
                        is TranslateEvent.RecordAudio -> {
                            navHostController.navigate(Routes.VOICE_TO_TEXT + "/${translateState.fromLanguage.language.languageCode}")
                        }
                        else -> {
                            translationViewModel.onTranslationEvent(translateEvent)
                        }

                    }
                }
            )
        }

        composable(route = Routes.VOICE_TO_TEXT + "/{languageCode}",
            arguments = listOf(
                navArgument(
                    name = "languageCode") {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )) {
            Text(text = "Voice to Text")
        }
    }
}