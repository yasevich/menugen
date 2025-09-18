package com.github.yasevich.menugen.di

import com.github.yasevich.menugen.data.UploadRepository
import com.github.yasevich.menugen.data.openai.OpenAiUploadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindUploadRepository(repository: OpenAiUploadRepository): UploadRepository
}
