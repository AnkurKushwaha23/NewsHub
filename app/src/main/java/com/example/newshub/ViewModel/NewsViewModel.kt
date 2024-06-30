package com.example.newshub.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newshub.Model.NewsModel
import com.example.newshub.Repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val newsData: LiveData<NewsModel> = repository._newsData

    fun getHeadlines(country: String, page: Int) {
        repository.fetchHeadlines(country, page)
    }

    fun getCategoryNews(country: String, category: String) {
        repository.fetchCategoryNews(country, category)
    }

    fun getSearchNews(search : String){
        repository.fetchSearchNews(search)
    }
}
