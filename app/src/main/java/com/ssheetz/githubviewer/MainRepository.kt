package com.ssheetz.githubviewer

import com.ssheetz.githubviewer.api.GithubApiService
import com.ssheetz.githubviewer.entities.Contributor
import com.ssheetz.githubviewer.entities.GitRepoInfo

class MainRepository(private val apiService: GithubApiService) {
    //todo: Put your Github token here
    val token = ""

    suspend fun getMostStarredRepositories(page: Int, perPage: Int): List<GitRepoInfo> {
        return apiService.getMostStarredRepositories(perPage = perPage, page = page).items
    }

    suspend fun getTopContributor(owner: String, repo: String): Contributor? {
        return apiService.getContributors("Bearer $token", owner, repo).maxByOrNull { it.contributions }
    }
}