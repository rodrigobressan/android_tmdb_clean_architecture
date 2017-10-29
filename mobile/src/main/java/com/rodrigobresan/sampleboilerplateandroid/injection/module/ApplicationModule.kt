package com.rodrigobresan.sampleboilerplateandroid.injection.module

import android.app.Application
import android.content.Context
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.db.mapper.movie.MovieDbMapper
import com.rodrigobresan.cache.impl.MovieCacheImpl
import com.rodrigobresan.cache.mapper.MovieEntityMapper
import com.rodrigobresan.data.executor.JobExecutor
import com.rodrigobresan.data.mapper.MovieMapper
import com.rodrigobresan.data.repository.movie.movie.MovieCache
import com.rodrigobresan.data.repository.movie.movie.MovieDataRepository
import com.rodrigobresan.data.repository.movie.movie.MovieRemote
import com.rodrigobresan.data.source.MovieDataStoreFactory
import com.rodrigobresan.domain.executor.PostExecutionThread
import com.rodrigobresan.domain.executor.ThreadExecutor
import com.rodrigobresan.domain.repository.MovieRepository
import com.rodrigobresan.remote.movies.impl.MovieRemoteImpl
import com.rodrigobresan.remote.service.MovieService
import com.rodrigobresan.remote.service.MovieServiceFactory
import com.rodrigobresan.sampleboilerplateandroid.BuildConfig
import com.rodrigobresan.sampleboilerplateandroid.UiThread
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun proviedeContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    internal fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    @Provides
    @PerApplication
    internal fun provideMovieRepository(factory: MovieDataStoreFactory,
                                        mapper: MovieMapper): MovieRepository {
        return MovieDataRepository(factory, mapper)
    }

    @Provides
    @PerApplication
    internal fun provideMovieCache(dbOpenHelper: DbOpenHelper,
                                   movieEntityMapper: MovieEntityMapper,
                                   movieDbMapper: MovieDbMapper,
                                   preferences: PreferencesHelper): MovieCache {
        return MovieCacheImpl(dbOpenHelper, movieEntityMapper, movieDbMapper, preferences)
    }

    @Provides
    @PerApplication
    internal fun provideMovieRemote(service: MovieService,
                                    entityMapper: com.rodrigobresan.remote.movies.mapper.MovieEntityMapper): MovieRemote {
        return MovieRemoteImpl(service, entityMapper)
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
    internal fun provideMovieService() : MovieService {
        return MovieServiceFactory.makeMovieService(BuildConfig.DEBUG)

    }
}