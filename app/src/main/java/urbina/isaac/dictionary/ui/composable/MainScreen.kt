package urbina.isaac.dictionary.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import urbina.isaac.dictionary.ui.theme.MainActivityViewModel

private const val TAG = "MainScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    val results by viewModel.results.collectAsState()
    val apiResponseItems = viewModel.apiResponse.collectAsLazyPagingItems()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            modifier = Modifier.fillMaxWidth(1F),
            value = text,
            onValueChange = { text = it },
            label = { Text("Search") }
        )
        Button(onClick = { viewModel.search(text) }) {
            Text("Search")
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(1F)
        ) {
            items(
                count = apiResponseItems.itemCount,
                key = apiResponseItems.itemKey { it.word },
                contentType = apiResponseItems.itemContentType { "contentType" }
            ) { index ->
                val item = apiResponseItems[index]
                ResultCard(apiResponse = item!!)
            }
            apiResponseItems.apply {
                when {
                    loadState.refresh is LoadState.Loading ->
                        Log.d(TAG, "load state refresh -> first time response page is loading")

                    loadState.refresh is LoadState.Error ->
                        Log.d(TAG, "load state refresh -> failed to load first page")

                    loadState.append is LoadState.Loading ->
                        Log.d(TAG, "load state append -> next response page is loading")

                    loadState.append is LoadState.Error ->
                        Log.d(TAG, "load state append -> failed to load next page")
                }
            }
        }
    }
}
