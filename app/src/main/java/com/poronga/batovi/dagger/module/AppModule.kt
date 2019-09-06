package com.poronga.batovi.dagger.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {
    @Singleton
    @Provides
    fun getGson() = Gson()
}