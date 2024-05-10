package com.example.searchcollector.activities.data.image

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ImageResponse(
    @SerializedName("documents")
    val documents: List<ImageInfo>,
    @SerializedName("meta")
    val meta: Meta
)

data class ImageInfo(
    @SerializedName("collection")
    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("display_sitename")
    val displaySiteName: String,
    @SerializedName("doc_url")
    val docUrl: String,
    @SerializedName("datetime")
    val dateTime: Date
)

data class Meta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)