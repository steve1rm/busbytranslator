package me.androidbox.busbytranslator.core.domain.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

actual open class CommonMutableStateFlow<T> actual constructor(private val mutableStateFlow: MutableStateFlow<T>)
    : CommonStateFlow<T>(mutableStateFlow), MutableStateFlow<T> {

    override var value: T
        get() = super.value
        set(value) {
            mutableStateFlow.value = value
        }

    override val replayCache: List<T>
        get() = mutableStateFlow.replayCache

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() {
        mutableStateFlow.resetReplayCache()
    }

    override val subscriptionCount: StateFlow<Int>
        get() = mutableStateFlow.subscriptionCount

    override fun compareAndSet(expect: T, update: T): Boolean {
        return mutableStateFlow.compareAndSet(expect, update)
    }

    override fun tryEmit(value: T): Boolean {
        return mutableStateFlow.tryEmit(value)
    }

    override suspend fun emit(value: T) {
        mutableStateFlow.emit(value)
    }
}