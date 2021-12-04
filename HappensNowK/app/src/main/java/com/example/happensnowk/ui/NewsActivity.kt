package com.example.happensnowk.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.happensnowk.R
import com.example.happensnowk.db.ArticleDatabase
import com.example.happensnowk.repository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val newsRepository = NewsRepository(ArticleDatabase(this@NewsActivity))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository,application)
        viewModel =ViewModelProvider (this,viewModelProviderFactory).get(NewsViewModel::class.java)

        initFragment()


    }

    private fun initFragment() {
        bottomNavigation = findViewById(R.id.bottomNavigationView)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        bottomNavigation.setupWithNavController(navHostFragment.findNavController())
    }
}