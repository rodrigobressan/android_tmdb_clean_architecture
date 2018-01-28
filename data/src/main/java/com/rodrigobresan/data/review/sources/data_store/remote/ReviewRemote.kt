package com.rodrigobresan.data.review.sources.data_store.remote

import com.rodrigobresan.data.review.model.ReviewEntity
import io.reactivex.Single

interface ReviewRemote {
    fun getReviews(movieId: Long): Single<List<ReviewEntity>>
}