package com.example.newshub.Api

import com.example.newshub.Api.NewsApiConstants.API_KEY
import com.example.newshub.Model.NewsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

object NewsApiConstants {
    const val BASE_URL = "https://newsapi.org"
    const val API_KEY = "c0e936db8b4b4035aa77e481229e43f2"
}

interface NewsInterface {
    @GET("/v2/top-headlines?apiKey=$API_KEY")
    fun getHeadLines(
        @Query("country") country: String,
        @Query("page") page: Int
    ): Call<NewsModel>
//    https://newsapi.org/v2/top-headlines?apiKey=c0e936db8b4b4035aa77e481229e43f2&country=in&page=1
    @GET("/v2/top-headlines?apiKey=$API_KEY")
    fun getCategory(
        @Query("country") country: String,
        @Query("category") category: String
    ): Call<NewsModel>
//    https://newsapi.org/v2/top-headlines?apiKey=c0e936db8b4b4035aa77e481229e43f2&country=in&category=business

    @GET("/v2/everything?apiKey=$API_KEY")
    fun getSearchItem(
        @Query("q") search : String
    ): Call<NewsModel>
//    https://newsapi.org/v2/everything?apiKey=c0e936db8b4b4035aa77e481229e43f2&q=allu%20arjun
}



