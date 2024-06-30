package com.example.newshub.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newshub.Api.NewsInterface
import com.example.newshub.Model.NewsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsInterface: NewsInterface){

    private val newsData = MutableLiveData<NewsModel>()
    val _newsData: LiveData<NewsModel> = newsData

    fun fetchHeadlines(country: String, page: Int) {
        val call = newsInterface.getHeadLines(country, page)
        call.enqueue(object : Callback<NewsModel> {
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                if (response.isSuccessful) {
                    val news = response.body()
                    newsData.postValue(news!!)
                    Log.d("HeadlineNewsResponse", "Success: ${news.articles}")
                } else {
                    Log.e("HeadlineNewsResponse", "Failure: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                Log.e("HeadlineNewsResponse", "Error: ${t.message}")
            }
        })
    }
    fun fetchCategoryNews(country: String, category: String) {
        val call = newsInterface.getCategory(country, category)
        call.enqueue(object : Callback<NewsModel> {
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                if (response.isSuccessful) {
                    val news = response.body()
                    newsData.postValue(news!!)
                    Log.d("CategoryNewsResponse", "Success: ${news.articles}")
                } else {
                    Log.e("CategoryNewsResponse", "Failure: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                Log.e("CategoryNewsResponse", "Error: ${t.message}")
            }
        })
    }
    fun fetchSearchNews(search : String){
        val call = newsInterface.getSearchItem(search)
        call.enqueue(object : Callback<NewsModel> {
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                if (response.isSuccessful) {
                    val news = response.body()
                    newsData.postValue(news!!)
                    Log.d("SearchNewsResponse", "Success: ${news.articles}")
                } else {
                    Log.e("SearchNewsResponse", "Failure: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                Log.e("SearchNewsResponse", "Error: ${t.message}")
            }
        })
    }
}