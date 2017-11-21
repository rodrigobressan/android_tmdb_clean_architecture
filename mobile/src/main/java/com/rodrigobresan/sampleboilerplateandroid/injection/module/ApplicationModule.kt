package com.rodrigobresan.sampleboilerplateandroid.injection.module

import android.app.Application
import android.content.Context
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.impl.CategoryCacheImpl
import com.rodrigobresan.cache.category.mapper.db.CategoryDbMapper
import com.rodrigobresan.cache.category.mapper.entity.CategoryCacheMapper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.mapper.MovieCacheMapper
import com.rodrigobresan.cache.movie.impl.MovieCacheImpl
import com.rodrigobresan.cache.movie.mapper.db.MovieCategoryCacheDbMapper
import com.rodrigobresan.cache.movie.mapper.db.MovieCacheDbMapper
import com.rodrigobresan.cache.movie_category.impl.MovieCategoryCacheImpl
import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryCacheMapper
import com.rodrigobresan.cache.movie_detail.impl.MovieDetailCacheImpl
import com.rodrigobresan.cache.movie_detail.mapper.db.MovieDetailCacheDbMapper
import com.rodrigobresan.cache.movie_detail.mapper.entity.MovieDetailCacheMapper
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.executor.JobExecutor
import com.rodrigobresan.data.movie.mapper.MovieMapper
import com.rodrigobresan.data.movie.sources.MovieDataRepository
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStoreFactory
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemote
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.data.movie_detail.mapper.MovieDetailMapper
import com.rodrigobresan.data.movie_detail.sources.MovieDetailDataRepository
import com.rodrigobresan.data.movie_detail.sources.data_store.MovieDetailDataStoreFactory
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCache
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemote
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.movie_detail.repository.MovieDetailRepository
import com.rodrigobresan.domain.movies.repository.MovieRepository
import com.rodrigobresan.remote.movie_detail.impl.MovieDetailRemoteImpl
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
    internal fun provideMovieDetailsRepository(movieDetailDataStoreFactory: MovieDetailDataStoreFactory,
                                               movieDetailMapper: MovieDetailMapper): MovieDetailRepository {
        return MovieDetailDataRepository(movieDetailDataStoreFactory, movieDetailMapper)
    }

    @Provides
    @PerApplication
    internal fun provideMovieCategoryCache(dbOpenHelper: DbOpenHelper,
                                           cacheDbMapper: MovieCategoryCacheDbMapper,
                                           cacheMapper: MovieCategoryCacheMapper,
                                           preferencesHelper: PreferencesHelper): MovieCategoryCache {
        return MovieCategoryCacheImpl(dbOpenHelper, cacheDbMapper, cacheMapper, preferencesHelper)
    }

    @Provides
    @PerApplication
    internal fun provideCategoryCache(dbOpenHelper: DbOpenHelper,
                                      categoryCacheMapper: CategoryCacheMapper,
                                      categoryDbMapper: CategoryDbMapper,
                                      preferences: PreferencesHelper): CategoryCache {
        return CategoryCacheImpl(dbOpenHelper, categoryCacheMapper, categoryDbMapper, preferences)
    }

    @Provides
    @PerApplication
    internal fun provideMovieCache(dbOpenHelper: DbOpenHelper,
                                   movieCacheMapper: MovieCacheMapper,
                                   movieCacheDbMapper: MovieCacheDbMapper,
                                   preferences: PreferencesHelper): MovieCache {
        return MovieCacheImpl(dbOpenHelper, movieCacheMapper, movieCacheDbMapper, preferences)
    }

    @Provides
    @PerApplication
    internal fun provideMovieDetailCache(dbOpenHelper: DbOpenHelper,
                                         cacheMapper: MovieDetailCacheMapper,
                                         cacheDbMapper: MovieDetailCacheDbMapper,
                                         preferences: PreferencesHelper): MovieDetailCache {
        return MovieDetailCacheImpl(dbOpenHelper, cacheMapper, cacheDbMapper, preferences)
    }

    @Provides
    @PerApplication
    internal fun provideMovieRemote(service: MovieService,
                                    remoteMapper: com.rodrigobresan.remote.movies.mapper.MovieRemoteMapper): MovieRemote {
        return MovieRemoteImpl(service, remoteMapper)
    }

    @Provides
    @PerApplication
    internal fun provideMovieDetailRemote(service: MovieService,
                                          remoteMapper: com.rodrigobresan.remote.movie_detail.mapper.MovieDetailRemoteMapper): MovieDetailRemote {
        return MovieDetailRemoteImpl(service, remoteMapper)
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