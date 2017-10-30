package com.rodrigobresan.domain.base.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}