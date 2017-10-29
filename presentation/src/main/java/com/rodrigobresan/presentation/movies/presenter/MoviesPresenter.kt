package com.rodrigobresan.presentation.movies.presenter

import com.rodrigobresan.domain.interactor.GetMovies
import com.rodrigobresan.domain.model.Movie
import com.rodrigobresan.domain.model.MovieCategory
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import io.reactivex.observers.DisposableSingleObserver
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MoviesPresenter @Inject constructor(val moviesView: MoviesContract.View,
                                          val getMoviesUseCase: GetMovies,
                                          val movieMapper: MovieMapper) : MoviesContract.Presenter {

    val moviesToLoad = ArrayList<MovieCategory>(EnumSet.allOf(MovieCategory::class.java))

    init {
        moviesView.setPresenter(this)
    }

    override fun start() {
        loadMovies()
    }

    override fun stop() {
        getMoviesUseCase.dispose()
    }

    override fun loadMovies() {
        moviesToLoad.forEach {
            moviesView.showProgress(it)
            moviesView.hideMovies(it)
            getMoviesUseCase.execute(MoviesSubscriber(it), it)
        }
    }

    inner class MoviesSubscriber(var category: MovieCategory) : DisposableSingleObserver<List<Movie>>() {
        override fun onError(e: Throwable) {
            moviesView.hideMovies(category)
            moviesView.hideProgress(category)
            moviesView.hideEmptyState(category)
            moviesView.showErrorState(category)
        }

        override fun onSuccess(movies: List<Movie>) {
            handleGetMoviesSuccess(category, movies)
        }
    }

    private fun handleGetMoviesSuccess(category: MovieCategory, movies: List<Movie>) {
        moviesView.hideErrorState(category)
        moviesView.hideProgress(category)

        if (movies.isNotEmpty()) {
            moviesView.hideEmptyState(category)
            moviesView.showMovies(category, movies.map { movieMapper.mapToView(it) })
        } else {
            moviesView.showEmptyState(category)
            moviesView.hideMovies(category)
        }
    }
}
