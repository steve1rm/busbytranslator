package me.androidbox.busbytranslator.data.history

import database.HistoryEntity
import me.androidbox.busbytranslator.translate.domain.history.HistoryItem

fun HistoryEntity.toHistoryItem(): HistoryItem {
    return HistoryItem(
        id = this.id,
        fromLanguageCode = this.fromLanguageCode,
        fromText = this.fromText,
        toLanguageCode = this.toLangaugeCode,
        toText = this.toText
    )
}