package com.poronga.batovi.dagger.component

import android.app.Application
import com.poronga.batovi.dagger.module.AppModule
import com.poronga.batovi.services.UserManager
import com.poronga.batovi.view.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(subject: MainActivity)
    fun inject(subject: UserManager)

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }
}