package com.rodrigobresan.tv.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
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
import com.rodrigobresan.remote.service.MovieService
import com.rodrigobresan.remote.service.MovieServiceFactory
import com.rodrigobresan.tv.BuildConfig
import com.rodrigobresan.tv.UiThread
import com.rodrigobresan.tv.injection.scope.PerApplication
import com.rodrigobresan.tv.util.ConnectionUtils
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
    fun providesAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DbConstants.DbConfig.FILE_NAME)
                .build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun provideReviewsDao(database: AppDatabase): ReviewDao {
        return database.reviewDao()
    }

    @Provides
    fun provideMovieDetailsDao(database: AppDatabase): MovieDetailsDao {
        return database.movieDetailsDao()
    }

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideMovieCategoryDao(database: AppDatabase): MovieCategoryDao {
        return database.movieCategoryDao()
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
    internal fun provideReviewRepository(factory: ReviewDataStoreFactory,
                                         mapper: ReviewMapper): ReviewRepository {
        return ReviewDataRepository(factory, mapper)
    }


    @Provides
    @PerApplication
    internal fun provideMovieDetailsRepository(movieDetailDataStoreFactory: MovieDetailDataStoreFactory,
                                               movieDetailMapper: MovieDetailMapper): MovieDetailRepository {
        return MovieDetailDataRepository(movieDetailDataStoreFactory, movieDetailMapper)
    }

    @Provides
    @PerApplication
    internal fun provideMovieCategoryCache(
            movieCategoryDao: MovieCategoryDao,
            cacheMapper: MovieCategoryCacheMapper,
            preferencesHelper: PreferencesHelper): MovieCategoryCache {
        return MovieCategoryCacheImpl(movieCategoryDao, cacheMapper, preferencesHelper)
    }

    @Provides
    @PerApplication
    internal fun provideCategoryCache(categoryDao: CategoryDao,
                                      categoryCacheMapper: CategoryCacheMapper,
                                      preferences: PreferencesHelper): CategoryCache {
        return CategoryCacheImpl(categoryDao, categoryCacheMapper, preferences)
    }

    @Provides
    @PerApplication
    internal fun provideMovieCache(movieDao: MovieDao,
                                   movieCacheMapper: MovieCacheMapper,
                                   preferences: PreferencesHelper): MovieCache {
        return MovieCacheImpl(movieDao, movieCacheMapper, preferences)
    }

    @Provides
    @PerApplication
    internal fun provideReviewCache(reviewDao: ReviewDao,
                                    reviewCacheMapper: ReviewCacheMapper): ReviewCache {
        return ReviewCacheImpl(reviewDao, reviewCacheMapper)
    }

    @Provides
    @PerApplication
    internal fun provideMovieDetailCache(movieDetailsDao: MovieDetailsDao,
                                         cacheMapper: MovieDetailCacheMapper,
                                         preferences: PreferencesHelper): MovieDetailCache {
        return MovieDetailCacheImpl(movieDetailsDao, cacheMapper, preferences)
    }

    @Provides
    @PerApplication
    internal fun provideMovieRemote(service: MovieService,
                                    remoteMapper: com.rodrigobresan.remote.movies.mapper.MovieRemoteMapper): MovieRemote {
        return MovieRemoteImpl(service, remoteMapper)
    }


    @Provides
    @PerApplication
    internal fun provideReviewRemote(service: MovieService,
                                     remoteMapper: ReviewRemoteMapper): ReviewRemote {
        return ReviewRemoteImpl(service, remoteMapper)
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