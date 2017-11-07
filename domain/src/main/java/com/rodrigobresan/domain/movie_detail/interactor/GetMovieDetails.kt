package com.rodrigobresan.domain.movie_detail.interactor

import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.interactor.SingleUseCase
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import com.rodrigobresan.domain.movie_detail.repository.MovieDetailRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetMovieDetails @Inject constructor(val movieDetailRepository: MovieDetailRepository,
                                               threadExecutor: ThreadExecutor,
                                               postExecutionThread: PostExecutionThread)
    : SingleUseCase<MovieDetail, Long>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(movieId: Long?): Single<MovieDetail> {
        if (movieId != null) {
            return movieDetailRepository.getMovieDetails(movieId)
        }

        throw IllegalArgumentException()
    }

}