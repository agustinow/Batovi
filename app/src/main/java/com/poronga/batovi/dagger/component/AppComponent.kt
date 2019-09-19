package com.poronga.batovi.dagger.component

import android.app.Application
import com.poronga.batovi.dagger.module.AppModule
import com.poronga.batovi.services.UserManager
import com.poronga.batovi.view.ui.main.MainActivity
import com.poronga.batovi.view.ui.main.fragments.MainCreateProject
import com.poronga.batovi.view.ui.project.ProjectActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(subject: MainActivity)
    fun inject(subject: UserManager)
    fun inject(subject: MainCreateProject)
    fun inject(subject: ProjectActivity)
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }
}