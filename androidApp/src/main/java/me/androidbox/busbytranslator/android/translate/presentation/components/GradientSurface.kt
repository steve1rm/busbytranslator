package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.gradientSurface(): Modifier {
    return composed(
        inspectorInfo = {
            this.name
        },
        factory = {
            if (isSystemInDarkTheme()) {
                Modifier.background(
                    brush = Brush.verticalGradient(listOf(
                        Color(0xFF23262E),
                        Color(0xFF212329)
                    )))
            }
            else {
                Modifier.background(
                    color = MaterialTheme.colors.surface)
            }
        })
}