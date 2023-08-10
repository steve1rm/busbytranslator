package me.androidbox.busbytranslator.core.domain.util

sealed interface Resource {
    class Success<T>(data: T?) : Resource
    class Error(throwable: Throwable) : Resource
}
