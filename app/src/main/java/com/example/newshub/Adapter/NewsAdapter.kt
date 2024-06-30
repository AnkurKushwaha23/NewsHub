package com.example.newshub.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newshub.Model.Article
import com.example.newshub.R

class NewsAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val newsImg: ImageView = view.findViewById(R.id.newsImg)
        private val newsTitle: TextView = view.findViewById(R.id.newsTitle)
        private val newsSource: TextView = view.findViewById(R.id.newsSource)

        fun bind(news: Article) {
            newsTitle.text = news.title
            newsSource.text = news.author
            if (news.urlToImage != null) {
                Glide.with(itemView).load(news.urlToImage).into(newsImg)
            } else {
                newsImg.setImageResource(R.drawable.newspaper)
            }
            itemView.setOnClickListener {
                onItemClick(news.url)
            }
        }
    }
}

class NewsDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}