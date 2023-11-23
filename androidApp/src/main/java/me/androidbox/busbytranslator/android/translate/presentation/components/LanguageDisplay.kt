package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.busbytranslator.android.TranslatorTheme
import me.androidbox.busbytranslator.core.domain.language.Language
import me.androidbox.busbytranslator.core.presentation.UiLanguage

@Composable
fun LanguageDisplay(
    uiLanguage: UiLanguage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallLanguageIcon(uiLanguage = uiLanguage)

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = uiLanguage.language.languageName,
            color = Color.LightGray)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLanguageDisplay() {
    TranslatorTheme {
        LanguageDisplay(uiLanguage = UiLanguage(me.androidbox.busbytranslator.R.drawable.english, Language.ENGLISH))
    }
}