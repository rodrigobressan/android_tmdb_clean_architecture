package com.rodrigobresan.cache.review.mapper

import com.rodrigobresan.cache.base.mapper.entity.EntityMapper
import com.rodrigobresan.cache.review.model.ReviewCached
import com.rodrigobresan.data.review.model.ReviewEntity
import javax.inject.Inject

open class ReviewCacheMapper @Inject constructor() : EntityMapper<ReviewCached, ReviewEntity> {
    override fun mapFromCached(cached: ReviewCached): ReviewEntity {
        return ReviewEntity(cached.id, cached.author, cached.content, cached.url)
    }

    override fun mapToCached(entity: ReviewEntity): ReviewCached {
        val reviewCached = ReviewCached(0, entity.author, entity.content, entity.url)
        reviewCached.id = entity.id

        return reviewCached
    }

}