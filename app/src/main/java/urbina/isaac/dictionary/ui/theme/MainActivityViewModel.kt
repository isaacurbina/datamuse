package urbina.isaac.dictionary.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import urbina.isaac.dictionary.model.APIResponse
import urbina.isaac.dictionary.repository.DataMuseRepository

class MainActivityViewModel(
    private val dataRepository: DataMuseRepository = DataMuseRepository.instance(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val myFlow: Flow<Int> = flow {
        for (i in 0..100) {
            emit(i)
            delay(500)
        }
    }

    val newFlow = myFlow.filter { it % 2 == 0 }.map { "Current value = $it" }

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>(
        replay = 10, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sharedFlow = _sharedFlow.asSharedFlow()

    val subCount = _sharedFlow.subscriptionCount

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

    fun increaseValue() {
        _stateFlow.value += 1
    }

    fun startSharedFlow() = viewModelScope.launch {
        for (i in 1..50) {
            _sharedFlow.emit(i)
            delay(2000)
        }
    }

    companion object {
        private const val TAG = "MainActivityViewModel"
    }
}