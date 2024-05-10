package com.example.searchcollector.activities.retrofit

import com.example.searchcollector.activities.data.image.ImageResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ImageSearch {
    @GET("image")
    suspend fun getImage(@QueryMap param: HashMap<String, String>): ImageResponse
}