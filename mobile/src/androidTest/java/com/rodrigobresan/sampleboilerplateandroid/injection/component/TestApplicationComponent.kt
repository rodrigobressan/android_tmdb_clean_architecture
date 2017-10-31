package com.rodrigobresan.sampleboilerplateandroid.injection.component

import android.app.Application
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.movies.repository.MovieRepository
import com.rodrigobresan.sampleboilerplateandroid.injection.ApplicationComponent
import com.rodrigobresan.sampleboilerplateandroid.injection.module.ActivityBindingModule
import com.rodrigobresan.sampleboilerplateandroid.injection.module.TestApplicationModule
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerApplication
import com.rodrigobresan.sampleboilerplateandroid.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = arrayOf(TestApplicationModule::class, ActivityBindingModule::class,
        AndroidSupportInjectionModule::class))
@PerApplication

interface TestApplicationComponent : ApplicationComponent {

    fun movieRepository(): MovieRepository

    fun postExecutionThread(): PostExecutionThread

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }
}