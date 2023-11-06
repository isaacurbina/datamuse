package urbina.isaac.dictionary.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import urbina.isaac.dictionary.model.APIResponse
import urbina.isaac.dictionary.repository.DataMuseSource

class MainActivityViewModel(
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var query: String? = null
    private var dataSource: DataMuseSource = DataMuseSource.instance(query)

    private val _results = MutableStateFlow<List<APIResponse>>(emptyList())
    val results = _results.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asSharedFlow()

    val apiResponse: Flow<PagingData<APIResponse>> = Pager(PagingConfig(pageSize = 100)) {
        dataSource = DataMuseSource.instance(query)
        dataSource
    }.flow.flowOn(ioDispatcher)
        .catch {
            Log.e(TAG, "apiResponse -> exception in flow", it)
        }//.cachedIn(viewModelScope)

    fun search(query: String) {
        this.query = query
        dataSource.invalidate()
    }

    companion object {
        private const val TAG = "MainActivityViewModel"
    }
}