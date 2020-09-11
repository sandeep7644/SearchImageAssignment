package com.example.assignment.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class GsonProvider {

    @Singleton
    @Provides
    fun provideGsonInstance(gsonBuilder: GsonBuilder) = gsonBuilder.create()

    @Singleton
    @Provides
    fun provideGsonBuilder() = GsonBuilder()


}