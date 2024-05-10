package com.example.searchcollector.activities.data.video

import com.example.searchcollector.activities.retrofit.NetWorkClient.searchVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoSearchRepository {
    suspend fun search(searchText:String, currentPage: Int): List<VideoInfo> {
        return withContext(Dispatchers.IO){
            searchVideo.getVideo(setUpImageParameter(searchText, currentPage)).documents
        }
    }

    private fun setUpImageParameter(search: String, currentPage: Int): HashMap<String, String> {
        return hashMapOf(
            "query" to search,
            "sort" to "recency",
            "page" to currentPage.toString()
        )
    }
}