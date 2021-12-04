package com.example.happensnowk.db

import android.content.Context
import androidx.room.*
import com.example.happensnowk.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase () {
    abstract fun getArticleDao():ArticleDao

    companion object{
        @Volatile
        private var instance:ArticleDatabase?= null
        private val LOCK=Any()

        operator fun  invoke(context :Context)= instance?: synchronized(LOCK){
                instance ?:creatDatabase(context).also{
                    instance=it
                }
        }

        private fun creatDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "artical_db.db"
            ).build()

    }
}