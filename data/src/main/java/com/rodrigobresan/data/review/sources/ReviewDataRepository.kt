package com.rodrigobresan.data.movie.sources

import com.rodrigobresan.data.movie.sources.data_store.ReviewDataStoreFactory
import com.rodrigobresan.data.review.mapper.ReviewMapper
import com.rodrigobresan.data.review.sources.data_store.local.ReviewRemoteDataStore
import com.rodrigobresan.domain.review.model.Review
import com.rodrigobresan.domain.review.repository.ReviewRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * ReviewDataRepository implementation
 */
class ReviewDataRepository @Inject constructor(private val factory: ReviewDataStoreFactory,
                                               private val reviewMapper: ReviewMapper) : ReviewRepository {
    override fun clearReviews(): Completable {
        return factory.retrieveCachedDataStore().clearReviews()
    }

    override fun saveReviews(movieId: Long, reviews: List<Review>): Completable {
        val reviewEntities = reviews.map { reviewMapper.mapToEntity(it) }
        return factory.retrieveCachedDataStore().saveReviews(movieId, reviewEntities)
    }

    override fun getReviews(movieId: Long): Single<List<Review>> {
        val dataStore = factory.retrieveDataStore()

        return dataStore.getReviews(movieId)
                .flatMap {
                    if (dataStore is ReviewRemoteDataStore) {
                        factory.retrieveCachedDataStore().saveReviews(movieId, it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }
                .map {
                    list ->
                    list.map {
                        listItem ->
                        reviewMapper.mapFromEntity(listItem)
                    }
                }
    }

}