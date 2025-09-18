package com.github.yasevich.menugen.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SerializationModule {

    @Provides
    @Singleton
    fun provideJson(modules: Set<@JvmSuppressWildcards SerializersModule>): Json {
        return Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                modules.forEach(::include)
            }
        }
    }
}
