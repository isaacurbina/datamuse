package urbina.isaac.dictionary.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import urbina.isaac.dictionary.model.APIResponse
import urbina.isaac.dictionary.service.retrofit.DataMuseService

internal class DataMuseRepositoryImpl(
    private val apiService: DataMuseService
) : DataMuseRepository {
    override suspend fun search(query: String): Flow<List<APIResponse?>?> = flow {
        emit(apiService.search(query))
    }
}