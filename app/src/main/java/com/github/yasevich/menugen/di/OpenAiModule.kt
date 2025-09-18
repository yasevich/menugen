package com.github.yasevich.menugen.di

import com.openai.client.OpenAIClient
import com.openai.client.okhttp.OpenAIOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OpenAiModule {

    @Provides
    @Singleton
    fun provideOpenAiClient(): OpenAIClient = OpenAIOkHttpClient.builder()
        .apiKey("")
        .build() // todo provide own OkHttpClient instance
}
