package com.dennydev.noflix.dependency

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
//import io.ktor.client.engine.

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    fun provideClient(): HttpClient {
        return HttpClient(Android) {
            install(HttpCookies) {
                // Configure cookie handling options here
                storage = AcceptAllCookiesStorage() // Example: accept all cookies
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging){
                logger=object: Logger {
                    override fun log(message: String) {
                        Log.d("HTTP Client", message)
                    }
                }
                level=LogLevel.ALL
            }
        }
    }
}

@dagger.Module
@InstallIn(SingletonComponent::class)
object ContextModule {
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @UnstableApi
    fun provideExoPlayer(
        @ApplicationContext context: Context
    ): ExoPlayer = ExoPlayer.Builder(context)
        .build()
}