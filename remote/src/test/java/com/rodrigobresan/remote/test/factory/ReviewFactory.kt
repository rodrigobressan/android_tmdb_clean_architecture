package com.rodrigobresan.remote.test.factory

import com.rodrigobresan.remote.review.model.Review
import com.rodrigobresan.remote.review.model.ReviewResponse

class ReviewFactory {
    companion object Factory {
        fun makeReviewsResponse(): ReviewResponse {
            var response = ReviewResponse()
            response.results = makeReviews()
            return response
        }

        fun makeReviews(): List<Review> {
            val listReviews = (1..10).map { Review("review_" + it, "Author", "Content", "Url") }
            return listReviews
        }

        fun makeReviewModel(): Review {
            return Review("id", "author", "content", "url")
        }
    }
}