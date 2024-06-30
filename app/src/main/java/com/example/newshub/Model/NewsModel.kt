package com.example.newshub.Model

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)