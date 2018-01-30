package com.rodrigobresan.cache.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.cache.movie_detail.model.MovieDetailsCached
import com.rodrigobresan.cache.review.model.ReviewCached
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.review.model.ReviewEntity

class ReviewFactory {

    companion object Factory {

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