package com.tamas.jablonkai.podcast.api

import com.tamas.jablonkai.podcast.api.model.GPodcast
import com.tamas.jablonkai.podcast.api.model.GTag
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GPodderApi {

    @GET("toplist/{size}.json")
    suspend fun getTopPodcasts(
        @Path("size") size: Int,
        @Query("scale_logo") scale: Int
    ): List<GPodcast>

    @GET("api/2/tags/{size}.json")
    suspend fun getTopTags(@Path("size") size: Int): List<GTag>

    @GET("/api/2/tag/{tag}/{size}.json")
    suspend fun getPodcastsForTag(
        @Path("tag") tag: String,
        @Path("size") size: Int,
        @Query("scale_logo") scale: Int
    ): List<GPodcast>

    companion object {
        private const val BASE_URL = "https://gpodder.net/"

        fun create(): GPodderApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GPodderApi::class.java)
        }
    }
}
