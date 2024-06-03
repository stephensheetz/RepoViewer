package com.ssheetz.githubviewer

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.ssheetz.githubviewer.entities.GitRepoInfo
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _gitRepositories = MutableLiveData<List<GitRepoInfo>>()
    val gitRepositories: LiveData<List<GitRepoInfo>> get() = _gitRepositories

    init {
        fetchRepositories()
    }

    private fun fetchRepositories() {
        viewModelScope.launch {
            // Fetch 10 at a time up to 100
            for (currentPage in 1..10) {
                val repos = try {
                    repository.getMostStarredRepositories(currentPage, 10)
                } catch (e: Exception) {
                    null
                }

                repos?.forEach { repo ->
                    val repo2 = try {
                        val topContributor =
                            repository.getTopContributor(repo.owner.login, repo.name)
                        repo.copy(
                            topContributor = topContributor
                        )
                    } catch (e: Exception) {
                        Log.d("MainViewModel", e.toString())
                        repo
                    }
                    _gitRepositories.postValue(
                        ((_gitRepositories.value
                            ?: emptyList()) + repo2).sortedByDescending { it.stargazers_count }
                    )
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                //val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                //val savedStateHandle = extras.createSavedStateHandle()

                return MainViewModel(
                    DependencyProvider.mainRepository
                ) as T
            }
        }
    }
}
