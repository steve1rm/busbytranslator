package me.androidbox.busbytranslator.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

expect class CommonMutableStateFlow<T>(mutableStateFlow: MutableStateFlow<T>): MutableStateFlow<T>

fun <T> MutableStateFlow<T>.toCommonMutableStateFlow(): CommonMutableStateFlow<T> {
    return CommonMutableStateFlow(this)
}

