package com.example.happensnowk.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.happensnowk.R
import com.example.happensnowk.models.Article

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ArticleViewHolder> (){
    private var onItemClickListener:((Article)->Unit)?=null

    inner  class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var tvSourceTextView:TextView
        var tvTitleTextView:TextView
        var tvDescriptionTextView:TextView
        var tvPublishedAtTextView:TextView
        var currentArticle: Article?=null
        init {itemView.apply {
            itemImage = findViewById(R.id.ivArticleImage)
            tvSourceTextView = findViewById(R.id.tvSource)
            tvTitleTextView = findViewById(R.id.tvTitle)
            tvDescriptionTextView = findViewById(R.id.tvDescription)
            tvPublishedAtTextView = findViewById(R.id.tvPublishedAt)
            setOnClickListener({
                onItemClickListener?.let { it(currentArticle!!)
                }


            })
        }

        }


//        override fun onClick(v: View?) {
//            onItemClickListener?.let {
//                (currentArticle!!)
//            }?: Log.e("onClickListerner","isEmpty")
//        }
        fun setData(article: Article?, position:Int){
            Glide.with(itemView).load(article?.urlToImage).into(itemImage)
            tvSourceTextView.text=article?.source?.name
            tvTitleTextView.text=article?.title
            tvDescriptionTextView.text=article?.description
            tvPublishedAtTextView.text=article?.publishedAt

            this.currentArticle=article


        }
    }
    private val differCallback=object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
//        holder.apply {
//            Glide.with(itemView).load(article.urlToImage).into(itemImage)
//            tvSourceTextView.text=article.source.name
//            tvTitleTextView.text=article.title
//            tvDescriptionTextView.text=article.description
//            tvPublishedAtTextView.text=article.publishedAt
//            setOnItemClickListener {
//                onItemClickListener?.let {
//
//                    it(article)
//                }
//            }
//
//        }
        holder.setData(article,position)

    }

    override fun getItemCount(): Int {
        if(differ.currentList.size!=null){
        return differ.currentList.size}
        return 0
    }
    fun setOnItemClickListener(listener: ((Article)->Unit)?){
            onItemClickListener = listener

    }
}