package com.poronga.batovi.dagger.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.poronga.batovi.services.UserManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {
    @Singleton
    @Provides
    fun getGson(): Gson{
        return GsonBuilder()
            .setDateFormat("dd/MM/yyyy")
            .create()
    }
    @Singleton
    @Provides
    fun getUserManager() = UserManager({

    },{

    },{

    })
}