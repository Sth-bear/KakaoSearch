package com.example.searchcollector.activities.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.searchcollector.activities.data.AdapterItem
import com.example.searchcollector.activities.data.BookMarkItem
import com.example.searchcollector.activities.utils.convertDateFormat
import com.example.searchcollector.databinding.ItemSearchImgBinding
import com.example.searchcollector.databinding.ItemSearchVideoBinding
import com.squareup.picasso.Picasso

class BookMarkAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var itemList = listOf<BookMarkItem>()

    interface ItemClick {
        fun onClick(view: View, position: Int, item: BookMarkItem)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            1 -> {
                val binding = ItemSearchImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                imgHolder(binding)
            }
            2 -> {
                val binding = ItemSearchVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                videoHolder(binding)
            }
            else -> throw IllegalArgumentException("알 수 없는 뷰 타입")
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.itemView.setOnClickListener {
            itemClick?.onClick(it,position,itemList[position])
        }


        when(holder.itemViewType) {
            1 -> {
                (holder as imgHolder).bind(currentItem)
            }
            2 -> {
                (holder as videoHolder).bind(currentItem)
            }
        }
    }

    class imgHolder(private val binding: ItemSearchImgBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem: BookMarkItem) {
            Picasso.get()
                .load(imageItem.imageUrl)
                .fit()
                .centerCrop()
                .into(binding.ivItem)
            binding.apply {
                tvItemDate.text = convertDateFormat(imageItem.datetime)
                tvItemFrom.text = "[Image] ${imageItem.displaySiteName}"
            }
        }
        val bookMark = binding.ivItemBookmark
    }

    class videoHolder(private val binding: ItemSearchVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(videoItem: BookMarkItem) {
            Picasso.get()
                .load(videoItem.imageUrl)
                .fit()
                .centerCrop()
                .into(binding.ivItem)
            binding.apply {
                tvItemDate.text = convertDateFormat(videoItem.datetime)
                tvItemFrom.text = "[Video] ${videoItem.displaySiteName}"
            }
        }
    }

    fun listUpdate(items: List<BookMarkItem>) {
        this.itemList = items
        notifyDataSetChanged()
    }
}