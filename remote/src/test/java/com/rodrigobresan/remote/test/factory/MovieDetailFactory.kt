package com.rodrigobresan.remote.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.data.movie_detail.sources.MovieDetailRemote
import com.rodrigobresan.remote.movie_detail.model.MovieDetailResponse
import kotlin.reflect.jvm.internal.impl.utils.DFS

class MovieDetailFactory {
    companion object {
        fun makeMovieDetailResponse(): MovieDetailResponse {
            return MovieDetailResponse(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }
    }
}