package com.rodrigobresan.domain.movies.interactor

import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.interactor.CompletableUseCase
import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.repository.MovieRepository
import io.reactivex.Completable
import javax.inject.Inject

open class UnfavoriteMovie @Inject constructor(val movieRepository: MovieRepository,
                                               threadExecutor: ThreadExecutor,
                                               postExecutionThread: PostExecutionThread) :
        CompletableUseCase<Long>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Long): Completable {
        return movieRepository.getMovie(params)
                .flatMapCompletable {
                    movieRepository.deleteMovie(Category.FAVORITE, it)
                }
    }

}