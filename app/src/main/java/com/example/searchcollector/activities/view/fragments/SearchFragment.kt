package com.example.searchcollector.activities.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.searchcollector.R
import com.example.searchcollector.activities.data.AdapterItem
import com.example.searchcollector.activities.utils.ProgressDialogFragment
import com.example.searchcollector.activities.view.adapters.SearchFragAdapter
import com.example.searchcollector.activities.viewmodels.BookMarkViewModel
import com.example.searchcollector.activities.viewmodels.SearchViewModel
import com.example.searchcollector.databinding.FragmentSearchBinding



class SearchFragment : Fragment() {

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val searchAdapter by lazy { SearchFragAdapter() }
    private val viewModel by  viewModels<SearchViewModel> { SearchViewModel.SearchVideModelFactory() }
    private lateinit var bookMarkViewModel : BookMarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val searchSharedPreferences = requireContext().getSharedPreferences("searchText", Context.MODE_PRIVATE)

        defaultRecyclerViewBind()
        btnSearchOnClick(searchSharedPreferences)
        continueScroll()
        loadSearchTextData(searchSharedPreferences)
        itemOnClick()
        floatingButtonShow()
        floatingGoToTopClick()

        return binding.root
    }

    private fun continueScroll(){
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.rvSearch.canScrollVertically(1) //스크롤이 끝에 닿으면 page +1
                    && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        viewModel.currentPageCountPlus()
                }
            }
        })
    }

    private fun floatingButtonShow() {
        var isTop = false
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && isTop) {
                    binding.btnGoToTopSearchFrag.visibility = View.GONE
                    isTop = false
                } else if(dy < 0 && !isTop) {
                    binding.btnGoToTopSearchFrag.visibility = View.VISIBLE
                    isTop = true
                }
            }
        })
    }

    private fun floatingGoToTopClick() {
        binding.btnGoToTopSearchFrag.setOnClickListener{
            binding.rvSearch.smoothScrollToPosition(0)
            binding.btnGoToTopSearchFrag.visibility = View.GONE
        }
    }


    private fun defaultRecyclerViewBind() {
        viewModel.searchResult.observe(viewLifecycleOwner, Observer { //검색결과 리스트가 나오면 반영
            searchAdapter.listUpdate(it)
        })
        viewModel.currentPage.observe(viewLifecycleOwner, Observer {
            val searchText = binding.etSearchFrag.text.toString()  // 끝에 닿아 page가 바뀌게 되면 다음페이지 검색 요청
            if(searchText.isNotEmpty()) {
                viewModel.setPageItems()
            }
        })
        viewModel.totalSearchResult.observe(viewLifecycleOwner, Observer{
            viewModel.setPageItems()
        })
        bookMarkViewModel = ViewModelProvider(requireActivity()).get(BookMarkViewModel::class.java)
        bookMarkViewModel.bookMarkItems.observe(viewLifecycleOwner, Observer {  // 즐겨찾기 아이템 삭제시 반영
            searchAdapter.notifyDataSetChanged()
        })
        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.layoutManager = StaggeredGridLayoutManager(2, 1) // 길이가 다르기에 staggeredgrid로 지정
    }


    private fun btnSearchOnClick(pref: SharedPreferences) {
        binding.btnSearchFrag.setOnClickListener {
            val keyBoard = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyBoard.hideSoftInputFromWindow(binding.etSearchFrag.windowToken,0)
            viewModel.setSearchPageDefault() // 첫 검색시 page1로
            viewModel.setSearchDataClear() // 첫 검색시 기존데이터 삭제
            val searchText = binding.etSearchFrag.text.toString()
            if(searchText.isNotEmpty()) {
                viewModel.search(searchText, requireActivity().supportFragmentManager)
            }
            saveSearchData(pref = pref)
        }
    }

    private fun itemOnClick() {
        searchAdapter.itemClick = object : SearchFragAdapter.ItemClick {
            override fun onClick(view: View, position: Int, item: AdapterItem) {
                bookMarkViewModel.saveItem(item)
                view.findViewById<ImageView>(R.id.iv_item_bookmark).visibility = View.VISIBLE // 클릭시 즐겨찾기 표시
            }
        }
    }



    private fun saveSearchData(pref: SharedPreferences) {
        val edit = pref.edit()
        edit.putString("lastSearch", binding.etSearchFrag.text.toString())
        edit.apply() // 검색어 저장
    }

    private fun loadSearchTextData(pref: SharedPreferences) {
        binding.etSearchFrag.setText(pref.getString("lastSearch", "")) //검색어 불러오기
    }

}