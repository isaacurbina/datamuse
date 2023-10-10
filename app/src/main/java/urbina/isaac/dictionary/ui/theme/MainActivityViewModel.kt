package urbina.isaac.dictionary.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import urbina.isaac.dictionary.repository.DataMuseRepository

internal class MainActivityViewModel(
    private val dataRepository: DataMuseRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun search(query: String) = viewModelScope.launch {
        dataRepository.search(query)
            .flowOn(ioDispatcher)
            .catch { e ->
                Log.e(TAG, "error while searching for '$query'", e)
            }
            .collect {
                Log.i(TAG, "collected ${it?.size} results")
            }
    }

    companion object {
        private const val TAG = "MainActivityViewModel"
    }
}