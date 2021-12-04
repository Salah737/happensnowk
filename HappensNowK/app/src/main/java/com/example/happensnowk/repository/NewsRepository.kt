package com.example.happensnowk.repository

import com.example.happensnowk.api.RetrofitInstance
import com.example.happensnowk.db.ArticleDatabase
import com.example.happensnowk.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository (
    val db:ArticleDatabase
        ){
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
    suspend fun searchNews(searchQuery:String ,pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)
    suspend fun upsert(article: Article)=db.getArticleDao().upsert(article)

    fun getSavedNews()=db.getArticleDao().getAllArticales()

    suspend fun deleteArticle(article: Article)=db.getArticleDao().deleteArtical(article)
}