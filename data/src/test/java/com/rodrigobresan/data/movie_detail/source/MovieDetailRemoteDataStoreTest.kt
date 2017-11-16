package com.rodrigobresan.data.movie_detail.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemote
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemoteDataStore
import com.rodrigobresan.data.test.factory.MovieDetailFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailRemoteDataStoreTest {

    lateinit var remoteDataStore: MovieDetailRemoteDataStore
    lateinit var movieDetailRemote: MovieDetailRemote

    @Before
    fun setUp() {
        movieDetailRemote = mock()
        remoteDataStore = MovieDetailRemoteDataStore(movieDetailRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun throwExceptionWhenCallClearMovieDetails() {
        remoteDataStore.clearMovieDetails()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun throwExceptionWhenCallSaveMovieDetails() {
        remoteDataStore.saveMovieDetails(MovieDetailFactory.makeMovieDetailEntity())
    }

    @Test
    fun getMovieDetailsCallsRemoteDataStore() {
        remoteDataStore.getMovieDetails(0)

        verify(movieDetailRemote).getMovieDetails(0)
    }
}