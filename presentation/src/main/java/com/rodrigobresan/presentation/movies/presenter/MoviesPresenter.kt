package com.rodrigobresan.presentation.movies.presenter

import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.interactor.GetMovies
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import io.reactivex.observers.DisposableSingleObserver
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Implementation of Presenter for Movies section
 */
class MoviesPresenter @Inject constructor(val moviesView: MoviesContract.View,
                                          val getMoviesUseCase: GetMovies,
                                          val movieMapper: MovieMapper) : MoviesContract.Presenter {

    /**
     * The movie list to be loaded
     */
    val moviesToLoad = ArrayList<Category>(EnumSet.allOf(Category::class.java))

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

    inner class MoviesSubscriber(var category: Category) : DisposableSingleObserver<List<Movie>>() {
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

    private fun handleGetMoviesSuccess(category: Category, movies: List<Movie>) {
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
