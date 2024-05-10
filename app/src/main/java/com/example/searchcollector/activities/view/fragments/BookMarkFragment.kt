package com.example.searchcollector.activities.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.searchcollector.activities.data.AdapterItem
import com.example.searchcollector.activities.data.BookMarkItem
import com.example.searchcollector.activities.view.adapters.BookMarkAdapter
import com.example.searchcollector.activities.view.adapters.SearchFragAdapter
import com.example.searchcollector.activities.viewmodels.BookMarkViewModel
import com.example.searchcollector.databinding.FragmentBookMarkBinding


class BookMarkFragment : Fragment() {
    private val binding by lazy { FragmentBookMarkBinding.inflate(layoutInflater) }
    private lateinit var viewModel: BookMarkViewModel
    private val bookAdapter by lazy { BookMarkAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(BookMarkViewModel::class.java)
        viewModel.bookMarkItems.observe(viewLifecycleOwner, Observer { items ->
            bookAdapter.listUpdate(items)
        })
        defaultBookMarkRecyclerViewBind()
        itemOnClick()
        return binding.root
    }



    private fun defaultBookMarkRecyclerViewBind() {
        with(binding.rvBookmark) {
            viewModel.default()
            adapter = bookAdapter
            layoutManager = StaggeredGridLayoutManager(2, 1)
        }
    }

    private fun itemOnClick() {
        bookAdapter.itemClick = object : BookMarkAdapter.ItemClick {
            override fun onClick(view: View, position: Int, item: BookMarkItem) {
                viewModel.deleteItem(item)
            }
        }
    }


}