package com.rodrigobresan.tv.injection

import android.app.Application
import com.rodrigobresan.tv.MovieTvApplication
import com.rodrigobresan.tv.injection.module.ApplicationModule
import com.rodrigobresan.tv.injection.module.FragmentBindingModule
import com.rodrigobresan.tv.injection.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = arrayOf(FragmentBindingModule::class, ApplicationModule::class,
        AndroidSupportInjectionModule::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: MovieTvApplication)
}