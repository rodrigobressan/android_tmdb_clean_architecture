package com.rodrigobresan.cache.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.cache.review.model.ReviewCached
import com.rodrigobresan.data.review.model.ReviewEntity

class ReviewFactory {

    companion object Factory {

        fun makeReviewCachedList(size: Int) : List<ReviewCached> {
            var reviewList = arrayListOf<ReviewCached>()

            (1..size).forEach {
                reviewList.add(ReviewCached(0, "author", "content", "url"))
            }

            return reviewList
        }
        fun makeReviewCached(): ReviewCached {
            return ReviewCached(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makeReviewEntity(): ReviewEntity {
            return ReviewEntity(DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid())
        }
    }
}