package com.rodrigobresan.remote.movies.mapper

import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.remote.EntityMapper
import com.rodrigobresan.remote.review.model.Review
import javax.inject.Inject

open class ReviewRemoteMapper @Inject constructor() : EntityMapper<Review, ReviewEntity> {
    override fun mapRemoteToEntity(type: Review): ReviewEntity {
        return ReviewEntity(type.id, type.author, type.content, type.url)
    }


}