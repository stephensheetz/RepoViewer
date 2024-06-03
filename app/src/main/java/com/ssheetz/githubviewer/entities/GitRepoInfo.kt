package com.ssheetz.githubviewer.entities

data class GitRepoInfo(
    val id: String,
    val name: String,
    val owner: User,
    val stargazers_count: Int,
    val topContributor: Contributor?
)
