package com.ssheetz.githubviewer

import com.ssheetz.githubviewer.api.GithubApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyProvider {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val githubApiService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }

    val mainRepository: MainRepository by lazy {
        MainRepository(githubApiService)
    }
}
