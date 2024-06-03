package com.ssheetz.githubviewer.api

import com.ssheetz.githubviewer.entities.Contributor
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/repositories")
    suspend fun getMostStarredRepositories(
        @Query("q") query: String = "stars:>0",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1
    ): GithubRepoResponse

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<Contributor>
}
