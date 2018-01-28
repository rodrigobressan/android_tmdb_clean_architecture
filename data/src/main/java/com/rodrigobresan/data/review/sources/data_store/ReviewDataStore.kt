package com.rodrigobresan.data.review.sources.data_store

import com.rodrigobresan.data.review.model.ReviewEntity
import io.reactivex.Completable
import io.reactivex.Single


interface ReviewDataStore {

    fun clearReviews(): Completable

    fun getReviews(movieId: Long): Single<List<ReviewEntity>>

    fun saveReviews(movieId: Long, reviews: List<ReviewEntity>): Completable

}