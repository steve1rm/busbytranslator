package me.androidbox.busbytranslator.translate.domain.history

import me.androidbox.busbytranslator.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun getHistory(): CommonFlow<List<HistoryItem>>
    suspend fun insertHistory(historyItem: HistoryItem)
}