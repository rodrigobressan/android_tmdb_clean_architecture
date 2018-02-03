package com.rodrigobresan.domain.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.review.model.Review

class ReviewFactory {
    companion object Factory {
        fun makeReviewList(count: Int): List<Review> {
            val listReviews = arrayListOf<Review>()

            (1..count).forEach {
                listReviews.add(makeReview())
            }

            return listReviews
        }

        fun makeReview(): Review {
            return Review(DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid())
        }
    }
}