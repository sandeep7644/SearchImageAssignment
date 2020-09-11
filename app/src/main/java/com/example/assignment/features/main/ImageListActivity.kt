package com.example.assignment.features.main

import android.app.SearchManager
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.example.assignment.R
import com.example.assignment.features.add_comment.AddCommentActivity
import com.example.assignment.features.common.ImagesLoadStateAdapter
import com.example.assignment.utils.Constants
import com.example.assignment.utils.getExceptionString
import com.example.assignment.utils.setToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_image_list.*
import kotlinx.android.synthetic.main.toolbar_home.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ImageListActivity : AppCompatActivity() {
    private var searchJob: Job? = null
    var sharedPreferences: SharedPreferences? = null

    @ExperimentalPagingApi
    private val imageListViewModel: ImageListViewModel by viewModels()
    var searchQueryString: String = ""
    lateinit var imagesListAdapter: ImageListRecyclerviewAdapter

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        sharedPreferences = getSharedPreferences(Constants.MY_SHAREDPREFERENCE, MODE_PRIVATE)
        swipe_refresh.setOnRefreshListener {
            searchImages(true)
            swipe_refresh.isRefreshing = false
        }
        initAdapter()
        retry_button.setOnClickListener {
            searchImages(true)
        }
        searchQueryString =
            sharedPreferences?.getString(LAST_QUERY, DEFAULT_SEARCH_QUERY).toString()
        if (searchQueryString.isEmpty()) {
            nothing_text.visibility = VISIBLE
        }
        searchImages(false, false)
        setUpToolbar()
    }

    @ExperimentalPagingApi
    private fun initAdapter() {
        imagesListAdapter = ImageListRecyclerviewAdapter {
            AddCommentActivity.startActivity(this, it)
        }
        recyclerView.adapter = imagesListAdapter
        recyclerView.adapter = imagesListAdapter.withLoadStateHeaderAndFooter(
            header = ImagesLoadStateAdapter { imagesListAdapter.retry() },
            footer = ImagesLoadStateAdapter { imagesListAdapter.retry() }

        )
        imagesListAdapter.addLoadStateListener {
            recyclerView.isVisible = it.refresh is LoadState.NotLoading
            progress_bar.isVisible = it.refresh is LoadState.Loading
            retry_button.isVisible = it.refresh is LoadState.Error

            if (it.refresh is LoadState.Error)
                showToast((it.refresh as LoadState.Error).error.getExceptionString())
        }


    }


    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    @ExperimentalPagingApi
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (searchQueryString.equals(query)) {
                    return true
                }
                searchQueryString = query
                searchImages(true, false)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (searchQueryString.equals(newText)) {
                    return true
                }
                searchQueryString = newText
                searchImages(true)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    @ExperimentalPagingApi
    private fun searchImages(swipeRefresh: Boolean, debounce: Boolean = true) {
        if (!searchQueryString.isNullOrEmpty()) {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                if (debounce)
                    delay(600)
                sharedPreferences?.edit()?.putString(LAST_QUERY, searchQueryString)?.apply()
                toolbar.title = searchQueryString
                imageListViewModel.searchImages(swipeRefresh, searchQueryString)?.collectLatest {
                    if (nothing_text.isVisible) {
                        nothing_text.visibility = GONE
                    }
                    imagesListAdapter.submitData(it)

                }
            }
        }

    }


    private fun setUpToolbar() {
        setToolbar(toolbar, searchQueryString)
    }

    companion object {
        const val LAST_QUERY = "last_search_query"
        const val DEFAULT_SEARCH_QUERY = "Randome images"
    }


}