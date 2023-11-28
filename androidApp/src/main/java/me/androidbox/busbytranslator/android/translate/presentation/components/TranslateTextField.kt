package me.androidbox.busbytranslator.android.translate.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.busbytranslator.R.*
import me.androidbox.busbytranslator.android.R
import me.androidbox.busbytranslator.android.TranslatorTheme
import me.androidbox.busbytranslator.android.core.theme.LightBlue
import me.androidbox.busbytranslator.core.domain.language.Language
import me.androidbox.busbytranslator.core.presentation.UiLanguage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TranslateTextField(
    fromText: String,
    toText: String?,
    isTranslating: Boolean,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onTranslateClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    onTextFieldClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .clickable(onClick = onTextFieldClick)
            .padding(16.dp)
    ) {
        AnimatedContent(targetState = toText, label = "") {
            if(toText == null || isTranslating) {
                IdleTranslateTextField(
                    fromText = fromText,
                    isTranslating = isTranslating,
                    onTextChange = onTextChange,
                    onTranslateClick = onTranslateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2F))
            }
            else {
                TranslatedTextField(
                    fromText = fromText,
                    toText = toText,
                    fromLanguage = fromLanguage,
                    toLanguage = toLanguage,
                    onCopyClick = onCopyClick,
                    onCloseClick = onCloseClick,
                    onSpeakerClick = onSpeakerClick,
                    modifier = Modifier
                        .fillMaxWidth())
            }
        }
    }
}

@Composable
private fun TranslatedTextField(
    fromText: String,
    toText: String,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()) {

        LanguageDisplay(uiLanguage = fromLanguage)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = fromText,
            color = MaterialTheme.colors.onSurface)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            IconButton(onClick = {
                onCopyClick(fromText)
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.copy),
                    contentDescription = stringResource(R.string.copy),
                    tint = LightBlue)
            }

            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = LightBlue)
            }
        }

        Divider()

        LanguageDisplay(uiLanguage = toLanguage)

        Text(text = toText)

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            IconButton(onClick = {
                onCopyClick(toText)
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.copy),
                    contentDescription = stringResource(id = R.string.copy),
                    tint = LightBlue)
            }

            IconButton(onClick = onSpeakerClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.speaker),
                    contentDescription = stringResource(R.string.speaker),
                    tint = LightBlue)
            }
        }
    }
}

@Composable
private fun IdleTranslateTextField(
    fromText: String,
    isTranslating: Boolean,
    onTextChange: (String) -> Unit,
    onTranslateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = fromText,
            onValueChange = onTextChange,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            textStyle = TextStyle(
                color = MaterialTheme.colors.onSurface
            ),
            modifier = modifier
                .fillMaxSize()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
        )

        if(fromText.isBlank() && !isFocused) {
            Text(
                text = stringResource(id = R.string.enter_text_to_translate),
                color = Color.LightGray
            )
        }

        ProgressButton(
            text = stringResource(id = R.string.translate),
            isLoading = isTranslating,
            onClick = onTranslateClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewIdleTranslateTextField() {
    TranslatorTheme {
        IdleTranslateTextField(
            fromText = "Hello",
            isTranslating = false,
            onTextChange = { },
            onTranslateClick = {  })
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTranslateTextField() {
    TranslatorTheme {
        TranslatedTextField(
            fromText = "hello",
            toText = "world",
            fromLanguage = UiLanguage(drawable.english, Language.ENGLISH),
            toLanguage = UiLanguage(drawable.indonesian, Language.INDONESIAN),
            onCopyClick = {},
            onCloseClick = { /*TODO*/ },
            onSpeakerClick = { /*TODO*/ })
    }
}