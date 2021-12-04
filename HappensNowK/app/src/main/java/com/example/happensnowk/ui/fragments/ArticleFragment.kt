package com.example.happensnowk.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.happensnowk.R
import com.example.happensnowk.ui.NewsViewModel
import com.example.happensnowk.util.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArticleFragment:Fragment(R.layout.fragment_artical_preview) {
    val viewModel:NewsViewModel by activityViewModels()
    lateinit var webView:WebView
    lateinit var fab:FloatingActionButton
    val args: ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view)

    }
    private fun initUi(view:View){
        webView=view.findViewById(R.id.webView)
        fab = view.findViewById(R.id.fab)
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener({
            v->
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved successfully",Snackbar.LENGTH_SHORT).show()
        })
    }

}