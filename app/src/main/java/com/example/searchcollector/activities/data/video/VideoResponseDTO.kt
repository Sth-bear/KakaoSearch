package com.example.searchcollector.activities.data.video

import com.google.gson.annotations.SerializedName
import java.util.Date

data class VideoResponse (
    @SerializedName("documents")
    val documents: List<VideoInfo>,
    @SerializedName("meta")
    val meta: VideoMeta
)


data class VideoMeta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)

data class VideoInfo(
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("datetime")
    val dateTime: Date,
    @SerializedName("play_time")
    val playTime: Int,
    @SerializedName("thumbnail")
    val thumbNail : String,
    @SerializedName("author")
    val author: String
)