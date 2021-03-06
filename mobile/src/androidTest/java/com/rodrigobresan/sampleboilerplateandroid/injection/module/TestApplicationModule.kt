package com.rodrigobresan.sampleboilerplateandroid.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import com.rodrigobresan.cache.AppDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.dao.CategoryDao
import com.rodrigobresan.cache.category.impl.CategoryCacheImpl
import com.rodrigobresan.cache.category.mapper.entity.CategoryCacheMapper
import com.rodrigobresan.cache.db.DbConstants
import com.rodrigobresan.cache.mapper.MovieCacheMapper
import com.rodrigobresan.cache.movie.dao.MovieDao
import com.rodrigobresan.cache.movie.impl.MovieCacheImpl
import com.rodrigobresan.cache.movie_category.dao.MovieCategoryDao
import com.rodrigobresan.cache.movie_category.impl.MovieCategoryCacheImpl
import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryCacheMapper
import com.rodrigobresan.cache.movie_detail.dao.MovieDetailsDao
import com.rodrigobresan.cache.movie_detail.impl.MovieDetailCacheImpl
import com.rodrigobresan.cache.movie_detail.mapper.entity.MovieDetailCacheMapper
import com.rodrigobresan.cache.review.dao.ReviewDao
import com.rodrigobresan.cache.review.impl.ReviewCacheImpl
import com.rodrigobresan.cache.review.mapper.ReviewCacheMapper
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.executor.JobExecutor
import com.rodrigobresan.data.movie.mapper.MovieMapper
import com.rodrigobresan.data.movie.sources.MovieDataRepository
import com.rodrigobresan.data.movie.sources.ReviewDataRepository
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStoreFactory
import com.rodrigobresan.data.movie.sources.data_store.ReviewDataStoreFactory
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemote
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.data.movie_detail.mapper.MovieDetailMapper
import com.rodrigobresan.data.movie_detail.sources.MovieDetailDataRepository
import com.rodrigobresan.data.movie_detail.sources.data_store.MovieDetailDataStoreFactory
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCache
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemote
import com.rodrigobresan.data.review.mapper.ReviewMapper
import com.rodrigobresan.data.review.sources.data_store.local.ReviewCache
import com.rodrigobresan.data.review.sources.data_store.remote.ReviewRemote
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.movie_detail.repository.MovieDetailRepository
import com.rodrigobresan.domain.movies.repository.MovieRepository
import com.rodrigobresan.domain.review.repository.ReviewRepository
import com.rodrigobresan.remote.movie_detail.impl.MovieDetailRemoteImpl
import com.rodrigobresan.remote.movies.impl.MovieRemoteImpl
import com.rodrigobresan.remote.movies.mapper.ReviewRemoteMapper
import com.rodrigobresan.remote.review.impl.ReviewRemoteImpl
import com.rodrigobresan.remote.service.ApiConfiguration
import com.rodrigobresan.remote.service.MovieService
import com.rodrigobresan.remote.service.MovieServiceFactory
import com.rodrigobresan.sampleboilerplateandroid.BuildConfig
import com.rodrigobresan.sampleboilerplateandroid.UiThread
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerApplication
import com.rodrigobresan.sampleboilerplateandroid.util.ConnectionUtils
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
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    fun providesAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DbConstants.DbConfig.FILE_NAME)
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return mock()
    }

    @Provides
    fun provideReviewsDao(database: AppDatabase): ReviewDao {
        return mock()
    }

    @Provides
    fun provideMovieDetailsDao(database: AppDatabase): MovieDetailsDao {
        return mock()
    }

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return mock()
    }

    @Provides
    fun provideMovieCategoryDao(database: AppDatabase): MovieCategoryDao {
        return mock()
    }

    @Provides
    @PerApplication
    fun provideConnectionStatus(application: Application): ConnectionStatus {
        return mock()
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
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideReviewRepository(factory: ReviewDataStoreFactory,
                                         mapper: ReviewMapper): ReviewRepository {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieDetailsRepository(movieDetailDataStoreFactory: MovieDetailDataStoreFactory,
                                               movieDetailMapper: MovieDetailMapper): MovieDetailRepository {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieCategoryCache(
            movieCategoryDao: MovieCategoryDao,
            cacheMapper: MovieCategoryCacheMapper): MovieCategoryCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideReviewCache(reviewDao: ReviewDao,
                                    reviewCacheMapper: ReviewCacheMapper): ReviewCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideCategoryCache(categoryDao: CategoryDao,
                                      categoryCacheMapper: CategoryCacheMapper): CategoryCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieCache(movieDao: MovieDao,
                                   movieCacheMapper: MovieCacheMapper,
                                   preferences: PreferencesHelper): MovieCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieDetailCache(movieDetailsDao: MovieDetailsDao,
                                         cacheMapper: MovieDetailCacheMapper,
                                         preferences: PreferencesHelper): MovieDetailCache {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieRemote(service: MovieService,
                                    remoteMapper: com.rodrigobresan.remote.movies.mapper.MovieRemoteMapper): MovieRemote {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideMovieDetailRemote(service: MovieService,
                                          remoteMapper: com.rodrigobresan.remote.movie_detail.mapper.MovieDetailRemoteMapper): MovieDetailRemote {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideReviewRemote(service: MovieService,
                                     remoteMapper: ReviewRemoteMapper): ReviewRemote {
        return mock()
    }

    @Provides
    @PerApplication
    internal fun provideApiConfiguration(): ApiConfiguration {
        val apiUrl = BuildConfig.API_URL
        val apiKey = BuildConfig.API_KEY
        val apiConfiguration = ApiConfiguration(apiUrl, apiKey)
        return apiConfiguration
    }

    @Provides
    @PerApplication
    internal fun provideMovieService(apiConfiguration: ApiConfiguration): MovieService {
        return mock()
    }
}