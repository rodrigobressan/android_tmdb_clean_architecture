package com.rodrigobresan.data.review.mapper

import com.rodrigobresan.data.base.mapper.DataMapper
import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.domain.review.model.Review
import javax.inject.Inject


open class ReviewMapper @Inject constructor() : DataMapper<ReviewEntity, Review> {
    override fun mapFromEntity(entity: ReviewEntity): Review {
        return Review(entity.id, entity.author, entity.content, entity.url)
    }

    override fun mapToEntity(model: Review): ReviewEntity {
        return ReviewEntity(model.id, model.author, model.content, model.url)
    }

}