package com.rodrigobresan.domain.review.repository

import com.rodrigobresan.domain.review.model.Review
import io.reactivex.Completable
import io.reactivex.Single

interface ReviewRepository {

    fun clearReviews(): Completable

    fun getReviews(movieId: Long): Single<List<Review>>

    fun saveReviews(movieId: Long, reviews: List<Review>): Completable

}