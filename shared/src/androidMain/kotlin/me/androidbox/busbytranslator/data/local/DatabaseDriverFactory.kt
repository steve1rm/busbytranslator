package me.androidbox.busbytranslator.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import me.androidbox.busbytranslator.database.TranslateDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            schema = TranslateDatabase.Schema,
            context = context,
            name = "translate.db"
        )
    }
}