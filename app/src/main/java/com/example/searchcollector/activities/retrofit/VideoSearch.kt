package com.example.searchcollector.activities.retrofit

import com.example.searchcollector.activities.data.video.VideoResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface VideoSearch {
    @GET("vclip")
    suspend fun getVideo(@QueryMap param: HashMap<String, String>): VideoResponse
}