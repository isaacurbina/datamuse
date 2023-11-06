package urbina.isaac.dictionary.repository

import urbina.isaac.dictionary.model.APIResponse
import urbina.isaac.dictionary.service.retrofit.DataMuseService

internal class DataMuseRepositoryImpl(
    private val apiService: DataMuseService
) : DataMuseRepository {
    override suspend fun search(query: String, page: Int): List<APIResponse?>? =
        apiService.search(query, page)
}