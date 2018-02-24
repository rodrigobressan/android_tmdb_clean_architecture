package com.rodrigobresan.remote.service

import com.google.gson.FieldNamingPolicy
import com.nhaarman.mockito_kotlin.mock
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
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
    lateinit var apiConfiguration: ApiConfiguration

    @Before
    fun setUp() {
        requestChain = mock()
        val apiUrl = "http://apiurl.com/"
        val apiKey = "api_key"

        apiConfiguration = ApiConfiguration(apiUrl, apiKey)
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
    fun makeRetrofitCheckParams() {
        val gson = MovieServiceFactory.makeGson()
        val okHttpClient = MovieServiceFactory.makeOkHttpClient(apiConfiguration, true)
        val retrofit = MovieServiceFactory.makeRetrofit(apiConfiguration, okHttpClient,
                gson)

        assertEquals(retrofit.baseUrl().url().toString(), apiConfiguration.baseUrl)
    }

    @Test
    fun makeMovieServiceApiKeyInterceptor() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse())

        val okHttpClient = MovieServiceFactory.makeOkHttpClient(apiConfiguration, true)
        okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

        val request = mockWebServer.takeRequest()
        assertEquals("api_key", request.requestUrl.queryParameter(apiConfiguration.apiKey))

        mockWebServer.shutdown()
    }

    @Test
    fun makeMovieService() {
        val movieService = MovieServiceFactory.makeMovieService(apiConfiguration, true)
        Assert.assertThat(movieService, CoreMatchers.instanceOf(MovieService::class.java))
    }
}