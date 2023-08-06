package me.androidbox.busbytranslator.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(initialVAlue: T
) : CommonMutableStateFlow<T>(MutableStateFlow(initialVAlue))