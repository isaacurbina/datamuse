package urbina.isaac.dictionary.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import urbina.isaac.dictionary.model.APIResponse
import urbina.isaac.dictionary.repository.DataMuseRepository

class MainActivityViewModel(
    private val dataRepository: DataMuseRepository = DataMuseRepository.instance(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _results = MutableStateFlow<List<APIResponse>>(emptyList())
    val results = _results.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asSharedFlow()

    fun search(query: String) = viewModelScope.launch {
        dataRepository.search(query).flowOn(ioDispatcher).catch { e ->
            _error.emit(e.localizedMessage)
            Log.e(TAG, "error while searching for '$query'", e)
        }.collect {
            _results.emit(it?.filterNotNull().orEmpty())
            Log.i(TAG, "collected ${it?.size} results")
        }
    }

    companion object {
        private const val TAG = "MainActivityViewModel"
    }
}