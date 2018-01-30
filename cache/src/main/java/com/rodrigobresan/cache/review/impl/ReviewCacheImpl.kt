package com.rodrigobresan.cache.review.impl

import com.rodrigobresan.cache.review.dao.ReviewDao
import com.rodrigobresan.cache.review.mapper.ReviewCacheMapper
import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.data.review.sources.data_store.local.ReviewCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ReviewCacheImpl @Inject constructor(
        private val reviewDao: ReviewDao,
        private val reviewCacheMapper: ReviewCacheMapper) : ReviewCache {

    override fun clearReviews(): Completable {
        return Completable.defer {
            // TODO write clear reviews
            Completable.complete()
        }
    }

    override fun getReviews(movieId: Long): Single<List<ReviewEntity>> {
        return Single.defer<List<ReviewEntity>> {
            val reviews = reviewDao.getAllReviews(movieId)
            Single.just(reviews.map { reviewCacheMapper.mapFromCached(it) })
        }
    }

    override fun saveReviews(movieId: Long, reviews: List<ReviewEntity>): Completable {
        return Completable.defer {
            reviews.forEach {
                var cachedReview = reviewCacheMapper.mapToCached(it)
                cachedReview.movieId = movieId
                reviewDao.insert(cachedReview)
            }
            Completable.complete()
        }
    }

}