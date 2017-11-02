package com.rodrigobresan.data.executor

import com.rodrigobresan.domain.base.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Class for executing any job asynchronously
 */
open class JobExecutor @Inject constructor() : ThreadExecutor {

    private val workQueue: LinkedBlockingQueue<Runnable>
    private val threadFactory: ThreadFactory
    private val threadPoolExecutor: ThreadPoolExecutor

    init {
        this.workQueue = LinkedBlockingQueue()
        this.threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory)
    }

    override fun execute(runnable: Runnable?) {
        if (runnable == null) {
            throw IllegalArgumentException("Runnable to execute cannot be null")
        }

        this.threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {

        private var counter = 0

        override fun newThread(r: Runnable?): Thread {
            return Thread(r, THREAD_NAME + counter++)
        }

        companion object {
            private val THREAD_NAME = "android_"
        }
    }

    companion object {

        private val INITIAL_POOL_SIZE = 3
        private val MAX_POOL_SIZE = 5

        private val KEEP_ALIVE_TIME = 10

        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }
}
