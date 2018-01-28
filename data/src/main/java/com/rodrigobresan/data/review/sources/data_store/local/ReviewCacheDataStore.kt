package com.rodrigobresan.data.review.sources.data_store.local

import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.data.review.sources.data_store.ReviewDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class ReviewCacheDataStore @Inject constructor(
        private val reviewCache: ReviewCache)
    : ReviewDataStore {
    override fun clearReviews(): Completable {
        return reviewCache.clearReviews()
    }

    override fun getReviews(movieId: Long): Single<List<ReviewEntity>> {
        return reviewCache.getReviews(movieId)
    }

    override fun saveReviews(movieId: Long, reviews: List<ReviewEntity>): Completable {
        return reviewCache.saveReviews(movieId, reviews)
    }

}
