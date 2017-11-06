package com.rodrigobresan.sampleboilerplateandroid.injection.module

import android.app.Application
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.executor.JobExecutor
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemote
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.movies.repository.MovieRepository
import com.rodrigobresan.remote.service.MovieService
import com.rodrigobresan.sampleboilerplateandroid.UiThread
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class TestApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }


    @Provides
    @PerApplication
    internal fun providePreferencesHelper(): PreferencesHelper {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieRepository(): MovieRepository {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieCategoryCache(): MovieCategoryCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideCategoryCache(): CategoryCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieCache(): MovieCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieRemote(): MovieRemote {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @PerApplication
    internal fun provideMovieService(): MovieService {
        return mock()

    }
}