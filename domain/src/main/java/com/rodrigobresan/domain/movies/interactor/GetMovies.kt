package com.rodrigobresan.domain.movies.interactor

import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.interactor.SingleUseCase
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.movie_category.model.MovieCategory
import com.rodrigobresan.domain.movies.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case that returns a [Single] for the operation of fetching movies from a specified category
 */
open class GetMovies @Inject constructor(val movieRepository: MovieRepository,
                                         threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread) :

        SingleUseCase<List<Movie>, MovieCategory>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: MovieCategory?): Single<List<Movie>> {
        return movieRepository.getMovies(params!!)
    }

}