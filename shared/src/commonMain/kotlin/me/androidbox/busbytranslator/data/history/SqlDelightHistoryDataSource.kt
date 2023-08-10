package me.androidbox.busbytranslator.data.history

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import me.androidbox.busbytranslator.core.domain.util.CommonFlow
import me.androidbox.busbytranslator.core.domain.util.toCommonFlow
import me.androidbox.busbytranslator.database.TranslateDatabase
import me.androidbox.busbytranslator.translate.domain.history.HistoryDataSource
import me.androidbox.busbytranslator.translate.domain.history.HistoryItem

class SqlDelightHistoryDataSource(
    private val translateDatabase: TranslateDatabase
): HistoryDataSource {
    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return translateDatabase.translateQueries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map { historyItem ->
                    historyItem.toHistoryItem()
                }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistory(historyItem: HistoryItem) {
        translateDatabase.translateQueries.insertHistoryEntity(
            id = historyItem.id,
            fromLanguageCode = historyItem.fromLanguageCode,
            fromText = historyItem.fromText,
            toLangaugeCode = historyItem.toLanguageCode,
            toText = historyItem.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}
