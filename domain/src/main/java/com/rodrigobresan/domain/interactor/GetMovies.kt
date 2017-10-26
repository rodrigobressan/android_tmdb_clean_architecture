package com.rodrigobresan.domain.interactor

import com.rodrigobresan.domain.executor.PostExecutionThread
import com.rodrigobresan.domain.executor.ThreadExecutor
import com.rodrigobresan.domain.model.Movie
import com.rodrigobresan.domain.model.MovieCategory
import com.rodrigobresan.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetMovies @Inject constructor(val movieRepository: MovieRepository,
                                         threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread) :

        SingleUseCase<List<Movie>, MovieCategory>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: MovieCategory?): Single<List<Movie>> {
        return movieRepository.getMovies(params!!)
    }

}