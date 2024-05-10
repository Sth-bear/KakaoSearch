package com.example.searchcollector.activities.data.image

import com.example.searchcollector.activities.retrofit.NetWorkClient.searchImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageSearchRepository {
    suspend fun search(searchText:String, currentPage: Int): List<ImageInfo> {
        return withContext(Dispatchers.IO){
            searchImage.getImage(setUpImageParameter(searchText, currentPage)).documents
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