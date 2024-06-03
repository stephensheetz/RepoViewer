package com.ssheetz.githubviewer.api

import com.ssheetz.githubviewer.entities.GitRepoInfo

data class GithubRepoResponse(
    val items: List<GitRepoInfo>
)