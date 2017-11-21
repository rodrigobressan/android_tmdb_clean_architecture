package com.rodrigobresan.domain.movies.interactor

import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.interactor.SingleUseCase
import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.movies.repository.MovieRepository
import com.sun.javaws.exceptions.InvalidArgumentException
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case that returns a [Single] for the operation of fetching movies from a specified category
 */
open class GetMovies @Inject constructor(val movieRepository: MovieRepository,
                                         threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread) :

        SingleUseCase<List<Movie>, Category>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Category?): Single<List<Movie>> {
        if (params != null) {
            return movieRepository.getMovies(params)
        }

        throw InvalidArgumentException(arrayOf())
    }

}