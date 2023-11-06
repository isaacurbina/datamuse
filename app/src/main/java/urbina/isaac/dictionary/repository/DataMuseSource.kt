package urbina.isaac.dictionary.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import urbina.isaac.dictionary.model.APIResponse

class DataMuseSource(
    private val dataMuseRepository: DataMuseRepository,
    private val query: String?
) : PagingSource<Int, APIResponse>() {

    override fun getRefreshKey(state: PagingState<Int, APIResponse>): Int? {
        Log.d(TAG, "getRefreshKey: key is ${state.anchorPosition}")
        return null // state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, APIResponse> {
        if (query.isNullOrBlank()) {
            Log.d(TAG, "load: query is null or blank")
            return LoadResult.Error(IllegalArgumentException("query is null or blank"))
        }
        return try {
            val nextPage = (params.key ?: 0) + 1
            Log.d(TAG, "load: nextPage -> $nextPage for $query")
            val apiResponseList =
                dataMuseRepository.search(query, nextPage)
                    .orEmpty()
                    .filterNotNull()
                    .map {
                        // Adding page from pagination as a tag for visibility on UI only
                        val newTags = it.tags.toMutableList()
                        newTags.add("$nextPage")
                        it.copy(tags = newTags)
                    }
            LoadResult.Page(
                data = apiResponseList,
                prevKey = if (nextPage == 1) null else (nextPage - 1),
                nextKey = if (apiResponseList.isEmpty()) null else nextPage
            )
        } catch (exception: Exception) {
            Log.e(TAG, "load: error loading page for $query", exception)
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val TAG = "DataMuseSource"
        fun instance(query: String?): DataMuseSource =
            DataMuseSource(
                dataMuseRepository = DataMuseRepository.instance(),
                query = query
            )
    }
}
