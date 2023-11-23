package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.busbytranslator.android.TranslatorTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProgressButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(MaterialTheme.colors.primary)
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(targetState = isLoading, label = "") { isLoading ->
            if(isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colors.onPrimary,
                    strokeWidth = 2.dp
                )
            }
            else {
                Text(
                    text = text.uppercase(),
                    color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewProgressButtonInProgress() {
    TranslatorTheme {
        ProgressButton(
            text = "Translate",
            isLoading = true,
            onClick = { }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewProgressButton() {
    TranslatorTheme {
        ProgressButton(
            text = "Translate",
            isLoading = false,
            onClick = { }
        )
    }
}