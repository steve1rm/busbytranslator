package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.androidbox.busbytranslator.android.R
import me.androidbox.busbytranslator.android.TranslatorTheme
import me.androidbox.busbytranslator.android.core.theme.LightBlue
import me.androidbox.busbytranslator.core.domain.language.Language
import me.androidbox.busbytranslator.core.presentation.UiLanguage

@Composable
fun LanguageDropDown(
    uiLanguage: UiLanguage,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectedLanguage: (uiLanguage: UiLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = onDismiss
        ) {
            UiLanguage.allLanguages.forEach { uiLanguage ->
                LanguageDropDownItem(
                    uiLanguage = uiLanguage,
                    onClick = { onSelectedLanguage(uiLanguage) },
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = uiLanguage.languageImageRes,
                contentDescription = uiLanguage.language.languageName,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = uiLanguage.language.languageName,
                color = LightBlue
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = if (isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (isOpen) stringResource(id = R.string.close) else stringResource(
                    id = R.string.open
                ),
                tint = LightBlue,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLanguageDropDown() {
    TranslatorTheme {
        LanguageDropDown(
            uiLanguage = UiLanguage(me.androidbox.busbytranslator.R.drawable.english, Language.ENGLISH),
            isOpen = true,
            onClick = {},
            onDismiss = {},
            onSelectedLanguage = { },
            modifier = Modifier
        )
    }
}