package com.rodrigobresan.presentation.movies.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.domain.review.model.Review

class ReviewFactory {

    companion object Factory {
        fun makeReview(): Review {
            return Review(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makeReviewList(count: Int): List<Review> {
            val reviewList = mutableListOf<Review>()
            repeat(count) {
                reviewList.add(makeReview())
            }

            return reviewList
        }
    }
}