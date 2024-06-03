package com.ssheetz.githubviewer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssheetz.githubviewer.MainViewModel
import com.ssheetz.githubviewer.entities.GitRepoInfo
import com.ssheetz.githubviewer.ui.theme.RepoViewerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RepoViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RepositoryListScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RepoViewerTheme {
        Greeting("Android")
    }
}

@Composable
fun RepositoryListScreen(viewModel: MainViewModel) {
    val repositories by viewModel.gitRepositories.observeAsState(emptyList())
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (repositories.isEmpty()) {
            Text(
                text = "Nothing to show",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.headlineSmall
            )
        } else {
            LazyColumn(state = listState) {
                items(repositories) { repo ->
                    RepositoryItem(repo) {
                        // Handle navigation to detail screen?
                    }
                }
            }
        }
    }
}

@Composable
fun RepositoryItem(repo: GitRepoInfo, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Text(text = repo.name, style = MaterialTheme.typography.headlineSmall)
        Text(text = "Owner: ${repo.owner.login}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Stars: ${repo.stargazers_count}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Top Contributor: ${repo.topContributor?.login ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
    }
}