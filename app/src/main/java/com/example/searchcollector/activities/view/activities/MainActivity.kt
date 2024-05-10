package com.example.searchcollector.activities.view.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.searchcollector.R
import com.example.searchcollector.activities.view.fragments.BookMarkFragment
import com.example.searchcollector.activities.view.fragments.SearchFragment
import com.example.searchcollector.databinding.ActivityMainBinding

private const val TAG_SEARCH = "search_fragment"
private const val TAG_BOOK = "book_mark_fragment"



class MainActivity : AppCompatActivity() {
    private val  binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        setFragment(TAG_SEARCH, SearchFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navi_book -> setFragment(TAG_BOOK, BookMarkFragment())
                R.id.navi_search -> setFragment(TAG_SEARCH,SearchFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrame, fragment, tag)
        }

        val search = manager.findFragmentByTag(TAG_SEARCH)
        val book = manager.findFragmentByTag(TAG_BOOK)

        if (search != null){
            fragTransaction.hide(search)
        }

        if (book != null){
            fragTransaction.hide(book)
        }


        if (tag == TAG_SEARCH) {
            if (search!=null){
                fragTransaction.show(search)
            }
        }
        else if (tag == TAG_BOOK) {
            if (book != null) {
                fragTransaction.show(book)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}