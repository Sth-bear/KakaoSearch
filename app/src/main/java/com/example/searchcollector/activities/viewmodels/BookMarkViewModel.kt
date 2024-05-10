package com.example.searchcollector.activities.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.searchcollector.activities.data.AdapterItem
import com.example.searchcollector.activities.data.BookMarkItem
import com.google.gson.Gson

class BookMarkViewModel(application: Application): AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("bookMarkItems", Context.MODE_PRIVATE)
    private val itemNumberSP = "item_number"
    private val _bookMarkItems = MutableLiveData<MutableList<BookMarkItem>>()
    val bookMarkItems: LiveData<MutableList<BookMarkItem>> get() = _bookMarkItems

    fun default() {
        val allItems = getBookMarkItems()
        _bookMarkItems.value = allItems
    }

    private fun getItemNumber(): Long {
        return sharedPreferences.getLong(itemNumberSP, 0)
    }

    private fun saveItemNumber(itemNumber: Long) {
        sharedPreferences.edit().putLong(itemNumberSP,itemNumber).apply()
    }

    private fun convertToBookMarkItem(adapterItem:AdapterItem, num:Long): BookMarkItem {
        return BookMarkItem(adapterItem.datetime, adapterItem.displaySiteName, adapterItem.imageUrl, adapterItem.type, num)
    }

    private fun convertToAdapterItem(bookMarkItem: BookMarkItem): AdapterItem {
        return AdapterItem(bookMarkItem.datetime,bookMarkItem.displaySiteName,bookMarkItem.imageUrl,bookMarkItem.type)
    }



    fun saveItem(item:AdapterItem) {
        val itemKey = item.imageUrl
        val itemList: MutableList<BookMarkItem> = getBookMarkItems()
        val number: Long = getItemNumber()+1
        if(!sharedPreferences.contains(itemKey)) {
            val convertItem = convertToBookMarkItem(item,number)
            val json = Gson().toJson(convertItem)
            sharedPreferences.edit().putString(itemKey, json).apply()
            itemList.add(convertItem)
            saveItemNumber(number)
            itemList.sortBy { it.number }
            _bookMarkItems.value = itemList
        }
    }


    fun deleteItem(item: BookMarkItem) {
        val edit = sharedPreferences.edit()
        val itemList: MutableList<BookMarkItem> = getBookMarkItems()
        itemList.remove(item)
        edit.remove(item.imageUrl)
        edit.apply()
        itemList.sortBy { it.number }
        _bookMarkItems.value = itemList
    }

    private fun getBookMarkItems() : MutableList<BookMarkItem> {
        val allItems = sharedPreferences.all
        val bookMarkItemList = mutableListOf<BookMarkItem>()
        for ((key, value) in allItems) {
            if(value is String) {
                val item = Gson().fromJson(value, BookMarkItem::class.java)
                bookMarkItemList.add(item)
            }
        }
        return bookMarkItemList
    }

}