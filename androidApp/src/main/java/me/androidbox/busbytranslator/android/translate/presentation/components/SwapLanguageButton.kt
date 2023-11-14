package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.busbytranslator.android.R
import me.androidbox.busbytranslator.android.TranslatorTheme

@Composable
fun SwapLanguagesButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.background(
            color = MaterialTheme.colors.primary,
            shape = CircleShape)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.swap_languages),
            contentDescription = stringResource(id = R.string.swap_languages),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSwapLanguagesButton() {
    TranslatorTheme {
        SwapLanguagesButton(
            onClick = {},
            modifier = Modifier
        )
    }
}