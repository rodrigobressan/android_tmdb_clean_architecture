package com.rodrigobresan.remote.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MovieServiceFactory {

    fun makeMovieService(isDebug: Boolean): MovieService {
        val okHttpClient = makeOkHttpClient(isDebug)
        return makeMovieService(okHttpClient, makeGson())
    }

    fun makeOkHttpClient(isDebug: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(makeInterceptor(isDebug))
                .addInterceptor(makeApiKeyInterceptor())
                .build()
    }

    fun makeApiKeyInterceptor(): Interceptor {
        return Interceptor({
            var request = it.request()
            val url = request.url().newBuilder().addQueryParameter("api_key", "4696a23366342ca5c5a52b1b8706e474").build()
            request = request.newBuilder().url(url).build()
            it.proceed(request)
        })
    }

    fun makeMovieService(okHttpClient: OkHttpClient, gson: Gson): MovieService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(MovieService::class.java)
    }

    fun makeInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()

        if (isDebug) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return logging
    }

    fun makeGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }
}