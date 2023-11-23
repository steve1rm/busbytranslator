package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.androidbox.busbytranslator.core.presentation.UiLanguage

@Composable
fun SmallLanguageIcon(
    uiLanguage: UiLanguage,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = uiLanguage.languageImageRes,
        contentDescription = uiLanguage.language.languageName,
        modifier = modifier.size(24.dp))
}