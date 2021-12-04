package com.example.happensnowk.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.happensnowk.models.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun upsert(artical: Article):Long

    @Query("SELECT * FROM articles")
    fun getAllArticales() :LiveData<List<Article>>

    @Delete
    suspend fun deleteArtical(artical: Article)


}