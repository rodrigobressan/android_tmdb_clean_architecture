package com.rodrigobresan.tv

import android.app.Application
import android.app.Fragment
import com.facebook.stetho.Stetho
import com.rodrigobresan.tv.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

class MovieTvApplication : Application(), HasFragmentInjector {

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    @Inject lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

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


}