package com.rodrigobresan.sampleboilerplateandroid.injection.module

import android.app.Application
import android.content.Context
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.impl.CategoryCacheImpl
import com.rodrigobresan.cache.category.mapper.db.CategoryDbMapper
import com.rodrigobresan.cache.category.mapper.entity.CategoryEntityMapper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.mapper.MovieEntityMapper
import com.rodrigobresan.cache.movie.impl.MovieCacheImpl
import com.rodrigobresan.cache.movie.mapper.db.MovieCategoryDbMapper
import com.rodrigobresan.cache.movie.mapper.db.MovieDbMapper
import com.rodrigobresan.cache.movie_category.impl.MovieCategoryCacheImpl
import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryEntityMapper
import com.rodrigobresan.data.executor.JobExecutor
import com.rodrigobresan.data.movie.mapper.MovieMapper
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.MovieDataRepository
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemote
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStoreFactory
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.movies.repository.MovieRepository
import com.rodrigobresan.remote.movies.impl.MovieRemoteImpl
import com.rodrigobresan.remote.service.MovieService
import com.rodrigobresan.remote.service.MovieServiceFactory
import com.rodrigobresan.sampleboilerplateandroid.BuildConfig
import com.rodrigobresan.sampleboilerplateandroid.UiThread
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerApplication
import com.rodrigobresan.sampleboilerplateandroid.util.ConnectionUtils
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    fun provideConnectionStatus(application: Application): ConnectionStatus {
        return ConnectionUtils(application)
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
    internal fun provideMovieCategoryCache(dbOpenHelper: DbOpenHelper,
                                           dbMapper: MovieCategoryDbMapper,
                                           entityMapper: MovieCategoryEntityMapper,
                                           preferencesHelper: PreferencesHelper): MovieCategoryCache {
        return MovieCategoryCacheImpl(dbOpenHelper, dbMapper, entityMapper, preferencesHelper)
    }

    @Provides
    @PerApplication
    internal fun provideCategoryCache(dbOpenHelper: DbOpenHelper,
                                      categoryEntityMapper: CategoryEntityMapper,
                                      categoryDbMapper: CategoryDbMapper,
                                      preferences: PreferencesHelper): CategoryCache {
        return CategoryCacheImpl(dbOpenHelper, categoryEntityMapper, categoryDbMapper, preferences)
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
    internal fun provideMovieService(): MovieService {
        return MovieServiceFactory.makeMovieService(BuildConfig.DEBUG)

    }
}