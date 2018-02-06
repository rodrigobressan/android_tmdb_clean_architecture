package com.rodrigobresan.data.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.domain.review.model.Review

class ReviewFactory {
    companion object Factory {
        fun makeReviewEntity(): ReviewEntity {
            return ReviewEntity(DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makeReview(): Review {
            return Review(DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makeReviewEntityList(count: Int): List<ReviewEntity> {
            val listReviews = arrayListOf<ReviewEntity>()

            (1..count).forEach {
                listReviews.add(makeReviewEntity())
            }

            return listReviews
        }

        fun makeReviewList(count: Int): List<Review> {
            val listReviews = arrayListOf<Review>()

            (1..count).forEach {
                listReviews.add(makeReview())
            }

            return listReviews
        }
    }
}
