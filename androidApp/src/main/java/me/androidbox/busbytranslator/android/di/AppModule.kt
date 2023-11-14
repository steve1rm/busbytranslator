package me.androidbox.busbytranslator.android.di

import android.app.Application
import android.content.Context
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import me.androidbox.busbytranslator.data.history.SqlDelightHistoryDataSource
import me.androidbox.busbytranslator.data.local.DatabaseDriverFactory
import me.androidbox.busbytranslator.data.remote.HttpClientFactory
import me.androidbox.busbytranslator.data.translate.KtorTranslationClient
import me.androidbox.busbytranslator.database.TranslateDatabase
import me.androidbox.busbytranslator.translate.domain.history.HistoryDataSource
import me.androidbox.busbytranslator.translate.domain.translate.TranslateUseCase
import me.androidbox.busbytranslator.translate.domain.translate.TranslationClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslationClient(httpClient: HttpClient): TranslationClient {
        return KtorTranslationClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriverFactory(application: Application): SqlDriver {
        return DatabaseDriverFactory(application)
            .create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(sqlDriver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(sqlDriver))
    }

    @Provides
    @Singleton
    fun providesTranslateUseCase(
        translationClient: TranslationClient,
        historyDataSource: HistoryDataSource
    ): TranslateUseCase {
        return TranslateUseCase(
            translationClient = translationClient,
            historyDataSource = historyDataSource)
    }
}