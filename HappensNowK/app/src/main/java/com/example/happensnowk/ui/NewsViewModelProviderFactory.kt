package com.example.happensnowk.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happensnowk.repository.NewsRepository
import java.lang.IllegalArgumentException

class NewsViewModelProviderFactory(val repository: NewsRepository
,val app:Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {

            return NewsViewModel(repository,app) as T}
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}