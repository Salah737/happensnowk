package com.example.happensnowk.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happensnowk.R
import com.example.happensnowk.adapters.NewsAdapter
import com.example.happensnowk.models.Article
import com.example.happensnowk.ui.NewsViewModel
import com.example.happensnowk.util.Constants
import com.example.happensnowk.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.happensnowk.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    //    lateinit var viewModel:NewsViewModel
    val viewModel: NewsViewModel by activityViewModels()
    lateinit var newsAdapter: NewsAdapter
    lateinit var rvSearchNews: RecyclerView
    lateinit var paginationProgressBar: ProgressBar
    lateinit var searchEditText: EditText
    val TAG = "SearchNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel=(activity as NewsActivity).viewModel
        rvSearchNews = view.findViewById(R.id.rvSearchNews)
        paginationProgressBar = view.findViewById(R.id.paginationProgressBar)
        searchEditText =view.findViewById(R.id.etSearch)
        initRecyclerView()

        var job: Job?=null
        searchEditText.addTextChangedListener { editable->
            job?.cancel()
            job= MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())

                    }
                }

            }
        }


        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Error -> {
                    response.massege?.let { message ->
                        Toast.makeText(activity,"An error occourd: $message", Toast.LENGTH_LONG).show()

                        Log.e(TAG, "An error occured:$message")
                    }

                }
                is Resource.Loading -> {
                    initRecyclerView()
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages= newsResponse.totalResults/ Constants.QUERY_PAGE_SIZE +2
                        isLastPage=viewModel.searchNewsPage==totalPages
                        if(isLastPage){
                            rvSearchNews.setPadding(0,0,0,0)
                        }
                    }
                }
            }

        })


    }

    private fun hideProgressBar() {

        paginationProgressBar.visibility = View.INVISIBLE
        isLoading=false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading=true

    }
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.searchNews(searchEditText.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun initRecyclerView(){
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter= newsAdapter
            layoutManager= LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)

        }
        newsAdapter.setOnItemClickListener {
                article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment_preview,
                bundle
            )
        }

    }
}