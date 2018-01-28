package com.rodrigobresan.data.review.sources.data_store.local

import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.data.review.sources.data_store.ReviewDataStore
import com.rodrigobresan.data.review.sources.data_store.remote.ReviewRemote
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class ReviewRemoteDataStore @Inject constructor(
        private val reviewRemote: ReviewRemote)
    : ReviewDataStore {
    override fun clearReviews(): Completable {
        throw UnsupportedOperationException()
    }

    override fun getReviews(movieId: Long): Single<List<ReviewEntity>> {
        return reviewRemote.getReviews(movieId)
    }

    override fun saveReviews(movieId: Long, reviews: List<ReviewEntity>): Completable {
        throw UnsupportedOperationException()
    }

}
