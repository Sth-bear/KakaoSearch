package com.example.searchcollector.activities.retrofit

import android.util.Log
import com.squareup.picasso.BuildConfig
import io.github.cdimascio.dotenv.dotenv
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {
    private const val BASE_URL = "https://dapi.kakao.com/v2/search/"
    private val env = dotenv{
        directory = "/assets"
        filename = "env"
    }
    private val apiKey = env["API_KEY"]

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d("testInterceptor", message)
            }
        })
        if(BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "${apiKey}")
                    .build()
                chain.proceed(request)
            }
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val searchRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient()).build()

    val searchImage: ImageSearch = searchRetrofit.create(ImageSearch::class.java)
    val searchVideo: VideoSearch = searchRetrofit.create(VideoSearch::class.java)
}