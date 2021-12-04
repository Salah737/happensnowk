package com.example.happensnowk.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happensnowk.R
import com.example.happensnowk.adapters.NewsAdapter
import com.example.happensnowk.models.Article
import com.example.happensnowk.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SavedNewsFragment:Fragment(R.layout.fragment_saved_news) {
//    lateinit var viewModel:NewsViewModel
     val viewModel:NewsViewModel by activityViewModels()
    lateinit var newsAdapter:NewsAdapter
    lateinit var rvSavedNews: RecyclerView
    lateinit var paginationProgressBar : ProgressBar
    val TAG="SavedNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel=(activity as NewsActivity).viewModel
        initUi(view)


    }
    private fun hideProgressBar() {

        paginationProgressBar.visibility= View.INVISIBLE
    }
    private fun showProgressBar(){
        paginationProgressBar.visibility=View.VISIBLE

    }

    private fun initRecyclerView(){


    }
    private fun initUi(view:View){
        rvSavedNews= view.findViewById(R.id.rvSavedNews)
        newsAdapter = NewsAdapter()
        rvSavedNews.apply {
            adapter= newsAdapter
            layoutManager= LinearLayoutManager(activity)

        }
        newsAdapter.setOnItemClickListener (object :(Article)->Unit{
            override fun invoke(article: Article) {
                val bundle =Bundle().apply {
                    putSerializable("article",article)
                }
                findNavController().navigate(
                    R.id.action_savedNewsFragment_to_articleFragment_preview,
                    bundle
                )
            }
        })
        val itemTouchHelperCallback =object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                   val position = viewHolder.adapterPosition
                   val article = newsAdapter.differ.currentList[position]
                   viewModel.deletArticle(article)
                Snackbar.make(view,"Successfully deleted article",Snackbar.LENGTH_LONG).apply {

                    setAction("Undo"){
                         viewModel.saveArticle(article)
                    }
                    show()
                }

            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply{
            attachToRecyclerView(rvSavedNews)
        }
        viewModel.getSaveNews().observe(viewLifecycleOwner, androidx.lifecycle.Observer {articles->
             newsAdapter.differ.submitList(articles)
        })


    }

}