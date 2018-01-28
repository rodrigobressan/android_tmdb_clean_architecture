package com.rodrigobresan.remote.review.impl

import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.data.review.sources.data_store.remote.ReviewRemote
import com.rodrigobresan.remote.movies.mapper.ReviewRemoteMapper
import com.rodrigobresan.remote.service.MovieService
import io.reactivex.Single
import javax.inject.Inject

class ReviewRemoteImpl @Inject constructor(
        private val movieService: MovieService,
        private val reviewMapper: ReviewRemoteMapper) : ReviewRemote {

    override fun getReviews(movieId: Long): Single<List<ReviewEntity>> {
        return movieService.getMovieReviews(movieId)
                .map {
                    it.results.map {
                        reviewMapper.mapRemoteToEntity(it)
                    }
                }
    }

}