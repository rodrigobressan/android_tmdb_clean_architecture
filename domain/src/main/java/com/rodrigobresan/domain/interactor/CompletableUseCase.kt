package com.rodrigobresan.domain.interactor

import com.rodrigobresan.domain.executor.PostExecutionThread
import com.rodrigobresan.domain.executor.ThreadExecutor
import io.reactivex.Completable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<in Params> protected constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    private val subscription = Disposables.empty()

    protected abstract fun buildUseCaseObservable(params: Params): Completable

    fun execute(params: Params): Completable {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
    }

    fun unsubscribe() {
        if (!subscription.isDisposed) {
            subscription.dispose()
        }
    }

}