package com.example.searchcollector.activities.viewmodels

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.searchcollector.activities.data.AdapterItem
import com.example.searchcollector.activities.data.image.ImageInfo
import com.example.searchcollector.activities.data.image.ImageSearchRepository
import com.example.searchcollector.activities.data.video.VideoInfo
import com.example.searchcollector.activities.data.video.VideoSearchRepository
import com.example.searchcollector.activities.utils.ProgressDialogFragment
import kotlinx.coroutines.launch

class SearchViewModel(private val imgRepo: ImageSearchRepository, private val videoRepo: VideoSearchRepository): ViewModel() {
    private var _totalSearchResult = MutableLiveData<MutableList<AdapterItem>>()
    val totalSearchResult: LiveData<MutableList<AdapterItem>> = _totalSearchResult
    private val _searchResults = MutableLiveData<MutableList<AdapterItem>>()
    val searchResult : LiveData<MutableList<AdapterItem>> get() = _searchResults

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int>get() = _currentPage

    fun setPageItems() {
        val page = _currentPage.value?: 1
        val itemList = _totalSearchResult.value?.take(page*25)?.toMutableList() ?: mutableListOf()
        _searchResults.value = itemList
    }


    fun search(searchWord: String, fragmentManager: FragmentManager) {
        _totalSearchResult.value = mutableListOf()
        val progressDialogFragment = ProgressDialogFragment()
        progressDialogFragment.show(fragmentManager,"progressDialog")
        viewModelScope.launch {
            try {
                val images = mutableListOf<ImageInfo>()
                for (i in 1..50) {
                    images.addAll(imgRepo.search(searchWord, i))
                }
                val videos = mutableListOf<VideoInfo>()
                for (i in 1..15) {
                    videos.addAll(videoRepo.search(searchWord, i))
                }
                val itemList = imgToAdapterItem(images, videos)
                _totalSearchResult.value = itemList
            } finally {
                progressDialogFragment.dismiss()
            }
        }
    }

    private fun imgToAdapterItem(img: List<ImageInfo>, video: List<VideoInfo>): MutableList<AdapterItem> {
        val totalItems = mutableListOf<AdapterItem>()
        for (item in img) {
            val totalItem = AdapterItem(
                datetime = item.dateTime.toString(),
                displaySiteName = item.displaySiteName,
                imageUrl = item.thumbnailUrl,
                type = 1
            )
            totalItems.add(totalItem)
        }
        for(item in video) {
            val totalItem = AdapterItem(
                datetime = item.dateTime.toString(),
                displaySiteName = item.title,
                imageUrl = item.thumbNail,
                type = 2
            )
            totalItems.add(totalItem)
        }
        totalItems.sortByDescending { it.datetime }
        return totalItems
    }

    fun setSearchDataClear() {
        _searchResults.value = mutableListOf()
        Log.d("test", "setSearchDataClear: ${searchResult.value}")
    }

    fun setSearchPageDefault() {
        _currentPage.value = 1
    }

    fun currentPageCountPlus() {
        var current = _currentPage.value?: 1
        if (current < 169) {
            current++
        }
        _currentPage.value = current
    }

    class SearchVideModelFactory: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(imgRepo = ImageSearchRepository(), videoRepo = VideoSearchRepository()) as T
            }
            throw IllegalArgumentException("Unknown Viewmodel Class")
        }
    }
}