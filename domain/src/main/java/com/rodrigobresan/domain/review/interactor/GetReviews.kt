package com.rodrigobresan.domain.review.interactor

import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.interactor.SingleUseCase
import com.rodrigobresan.domain.review.model.Review
import com.rodrigobresan.domain.review.repository.ReviewRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetReviews @Inject constructor(val reviewRepository: ReviewRepository,
                                          threadExecutor: ThreadExecutor,
                                          postExecutionThread: PostExecutionThread)
    : SingleUseCase<List<Review>, Long>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Long?): Single<List<Review>> {
        if (params != null) {
            return reviewRepository.getReviews(params)
        }

        throw IllegalArgumentException()
    }

}