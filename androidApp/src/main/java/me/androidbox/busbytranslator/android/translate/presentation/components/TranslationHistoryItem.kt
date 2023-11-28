package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.busbytranslator.R.drawable
import me.androidbox.busbytranslator.android.TranslatorTheme
import me.androidbox.busbytranslator.core.domain.language.Language
import me.androidbox.busbytranslator.core.presentation.UiLanguage
import me.androidbox.busbytranslator.translate.presentation.UiHistoryItem

@Composable
fun TranslationHistoryItem(
    historyItem: UiHistoryItem,
    onClick: (UiHistoryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .padding(16.dp)
            .clickable {
                onClick(historyItem)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallLanguageIcon(uiLanguage = historyItem.fromLanguage)
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(text = historyItem.fromText,
                color = Color.LightGray,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallLanguageIcon(uiLanguage = historyItem.toLanguage)

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = historyItem.toText,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTranslationHistoryItem() {
    TranslatorTheme {
        TranslationHistoryItem(
            historyItem = UiHistoryItem(
                0L,
                "How are you today",
                "I am fine thank you for asking",
                fromLanguage = UiLanguage(drawable.english, Language.ENGLISH),
                toLanguage = UiLanguage(drawable.indonesian, Language.INDONESIAN)
            ),
            onClick = {})
    }
}

