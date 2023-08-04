package me.androidbox.busbytranslator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform