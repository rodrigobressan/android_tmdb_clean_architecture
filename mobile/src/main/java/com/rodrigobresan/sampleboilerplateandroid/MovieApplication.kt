package com.rodrigobresan.sampleboilerplateandroid

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.rodrigobresan.sampleboilerplateandroid.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MovieApplication : Application(), HasActivityInjector {

    @Inject lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        initDaggerComponent()
        initStetho()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initDaggerComponent() {
        DaggerApplicationComponent.builder()
                .application(this)
                .build().
                inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

}