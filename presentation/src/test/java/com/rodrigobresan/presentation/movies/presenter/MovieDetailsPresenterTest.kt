package com.rodrigobresan.presentation.movies.presenter

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.domain.movie_detail.interactor.GetMovieDetails
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import com.rodrigobresan.domain.movies.interactor.FavoriteMovie
import com.rodrigobresan.domain.movies.interactor.UnfavoriteMovie
import com.rodrigobresan.domain.review.interactor.GetReviews
import com.rodrigobresan.presentation.movie_details.contract.MovieDetailsContract
import com.rodrigobresan.presentation.movie_details.mapper.MovieDetailsMapper
import com.rodrigobresan.presentation.movie_details.presenter.MovieDetailsPresenter
import com.rodrigobresan.presentation.movies.factory.MovieDetailFactory
import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing MoviePresenter class
 */
@RunWith(JUnit4::class)
class MovieDetailsPresenterTest {

    private lateinit var movieDetailsPresenter: MovieDetailsPresenter
    private lateinit var movieDetailsView: MovieDetailsContract.View
    private lateinit var getMovieDetails: GetMovieDetails
    private lateinit var favoriteMovie: FavoriteMovie
    private lateinit var unfavoriteMovie: UnfavoriteMovie
    private lateinit var getReviews: GetReviews
    private lateinit var movieDetailsMapper: MovieDetailsMapper

    private lateinit var captorMovieDetail: KArgumentCaptor<DisposableSingleObserver<MovieDetail>>
    private lateinit var captorFavoriteMovie: KArgumentCaptor<DisposableCompletableObserver>

    private lateinit var connectionStatus: ConnectionStatus

    @Before
    fun setUp() {
        captorMovieDetail = argumentCaptor<DisposableSingleObserver<MovieDetail>>()
        captorFavoriteMovie = argumentCaptor<DisposableCompletableObserver>()

        connectionStatus = mock()
        movieDetailsView = mock()
        getMovieDetails = mock()
        getReviews = mock()
        movieDetailsMapper = mock()
        favoriteMovie = mock()
        unfavoriteMovie = mock()

        movieDetailsPresenter = MovieDetailsPresenter(connectionStatus, movieDetailsView,
                getMovieDetails, favoriteMovie, unfavoriteMovie, getReviews, movieDetailsMapper)
    }

    @Test
    fun loadMovieDetailsHideErrorState() {
        movieDetailsPresenter.loadMovieDetails(0)

        verify(getMovieDetails).execute(captorMovieDetail.capture(), eq(0))
        captorMovieDetail.firstValue.onSuccess(MovieDetailFactory.makeMovieDetail())
        verify(movieDetailsView).hideErrorState()
    }

    @Test
    fun loadMovieDetailsShowMovie() {
        val movie = MovieDetailFactory.makeMovieDetail()
        movieDetailsPresenter.loadMovieDetails(0)

        verify(getMovieDetails).execute(captorMovieDetail.capture(), eq(0))
        captorMovieDetail.firstValue.onSuccess(movie)

        verify(movieDetailsView).showMovieDetails(movieDetailsMapper.mapToView(movie))
    }

    @Test
    fun loadMovieDetailsShowErrorState() {
        movieDetailsPresenter.loadMovieDetails(0)

        verify(getMovieDetails).execute(captorMovieDetail.capture(), eq(0))
        captorMovieDetail.firstValue.onError(RuntimeException())
        verify(movieDetailsView).showErrorState()
    }

//    @Test
//    fun loadMovieDetailsShowEmptyStateWhenResponseIsNotFound() {
//        movieDetailsPresenter.loadMovieDetails(0)
//
//        verify(getMovieDetails).execute(captorMovieDetail.capture(), eq(0))
//        captorMovieDetail.firstValue.onSuccess(null)
//        verify(movieDetailsView).showEmptyState()
//    }

    @Test
    fun showOfflineModeWithCachedDataWhenNoConnectionAndNoData() {

        whenever(connectionStatus.isOffline())
                .thenReturn(true)

        movieDetailsPresenter.loadMovieDetails(0)
        verify(getMovieDetails).execute(captorMovieDetail.capture(), eq(0))
        captorMovieDetail.firstValue.onError(Exception())


        verify(movieDetailsView).showErrorState()
        verify(movieDetailsView).showOfflineModeNoCachedData()
    }


    @Test
    fun showOfflineModeWithCachedDataWhenNoConnectionAndWithData() {

        whenever(connectionStatus.isOffline())
                .thenReturn(true)

        val movie = MovieDetailFactory.makeMovieDetail()
        movieDetailsPresenter.loadMovieDetails(0)
        verify(getMovieDetails).execute(captorMovieDetail.capture(), eq(0))
        captorMovieDetail.firstValue.onSuccess(movie)

        verify(movieDetailsView).showMovieDetails(movieDetailsMapper.mapToView(movie))

        verify(movieDetailsView).showOfflineModeCachedData()
    }
}