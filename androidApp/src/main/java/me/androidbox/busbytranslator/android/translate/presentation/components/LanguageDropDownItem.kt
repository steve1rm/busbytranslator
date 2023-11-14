package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.busbytranslator.R
import me.androidbox.busbytranslator.android.TranslatorTheme
import me.androidbox.busbytranslator.core.domain.language.Language
import me.androidbox.busbytranslator.core.presentation.UiLanguage

@Composable
fun LanguageDropDownItem(
    uiLanguage: UiLanguage,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        modifier = modifier,
        onClick = {
            onClick()
        }) {
        Image(
            painter = painterResource(id = uiLanguage.languageImageRes),
            contentDescription = uiLanguage.language.languageName,
            modifier = modifier.size(40.dp)
        )

        Spacer(modifier = modifier.width(16.dp))

        Text(text = uiLanguage.language.languageName)
    }
}

@Composable
@Preview
fun PreviewLanguageDropDownItem() {
    TranslatorTheme {
        LanguageDropDownItem(
            uiLanguage = UiLanguage(R.drawable.english, Language.ENGLISH),
            onClick = { })
    }
}