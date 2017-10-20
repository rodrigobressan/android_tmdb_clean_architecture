package com.rodrigobresan.data.repository.movie

import com.rodrigobresan.data.model.MovieEntity
import io.reactivex.Single

interface MovieRemote {
    fun getPopularMovies() : Single<List<MovieEntity>>
}