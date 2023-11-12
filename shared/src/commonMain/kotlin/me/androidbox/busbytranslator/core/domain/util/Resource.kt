package me.androidbox.busbytranslator.core.domain.util

sealed interface Resource<out T> {
    data class Success<T>(val data: T?) : Resource<T>
    data class Error(val throwable: Throwable) : Resource<Nothing>
}
