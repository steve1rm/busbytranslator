package me.androidbox.busbytranslator.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import me.androidbox.busbytranslator.database.TranslateDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(
            schema = TranslateDatabase.Schema,
            name = "translate.db"
        )
    }
}