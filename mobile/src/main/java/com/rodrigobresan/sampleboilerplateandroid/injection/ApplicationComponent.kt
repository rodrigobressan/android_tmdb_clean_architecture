package com.rodrigobresan.sampleboilerplateandroid.injection

import android.app.Application
import com.rodrigobresan.sampleboilerplateandroid.MovieApplication
import com.rodrigobresan.sampleboilerplateandroid.injection.module.ActivityBindingModule
import com.rodrigobresan.sampleboilerplateandroid.injection.module.ApplicationModule
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = arrayOf(ActivityBindingModule::class, ApplicationModule::class,
        AndroidSupportInjectionModule::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: MovieApplication)
}