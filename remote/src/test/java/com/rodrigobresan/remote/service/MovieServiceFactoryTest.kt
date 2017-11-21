package com.rodrigobresan.remote.service

import com.google.gson.FieldNamingPolicy
import com.nhaarman.mockito_kotlin.mock
import okhttp3.Interceptor.Chain
import okhttp3.logging.HttpLoggingInterceptor
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MovieServiceFactoryTest {

    lateinit var requestChain: Chain

    @Before
    fun setUp() {
        requestChain = mock()
    }

    @Test
    fun makeInterceptorWithLoggingLevelForDebug() {
        val interceptor = MovieServiceFactory.makeLoggingInterceptor(true)
        val loggingLevel = interceptor.level

        assertEquals(loggingLevel, HttpLoggingInterceptor.Level.BODY)
    }

    @Test
    fun makeInterceptorWithLoggingLevelForRelease() {
        val interceptor = MovieServiceFactory.makeLoggingInterceptor(false)
        val loggingLevel = interceptor.level

        assertEquals(loggingLevel, HttpLoggingInterceptor.Level.NONE)
    }

    @Test
    fun makeGsonWithLowerCaseFieldNamingPolicy() {
        val gson = MovieServiceFactory.makeGson()
        assertEquals(gson.fieldNamingStrategy(), FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    }

    @Test
    fun movieServiceParamsAreProperly() {
        assertEquals(MovieServiceParams.FIELD_API_KEY, "api_key")
        assertEquals(MovieServiceParams.FIELD_API_VALUE, "4696a23366342ca5c5a52b1b8706e474")
        assertEquals(MovieServiceParams.BASE_URL, "https://api.themoviedb.org/3/")
    }

    @Test
    fun makeRetrofitCheckParams() {
        val gson = MovieServiceFactory.makeGson()
        val retrofit = MovieServiceFactory.makeRetrofit(MovieServiceFactory.makeOkHttpClient(true),
                gson)

        assertEquals(retrofit.baseUrl().url().toString(), MovieServiceParams.BASE_URL)
    }

    @Test
    fun makeMovieService() {
        val movieService = MovieServiceFactory.makeMovieService(true)
        Assert.assertThat(movieService, CoreMatchers.instanceOf(MovieService::class.java))
    }
}