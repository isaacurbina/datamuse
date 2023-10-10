package urbina.isaac.dictionary.repository

import kotlinx.coroutines.flow.Flow
import urbina.isaac.dictionary.model.APIResponse
import urbina.isaac.dictionary.service.retrofit.DataMuseRetrofit

interface DataMuseRepository {

    suspend fun search(query: String): Flow<List<APIResponse?>?>

    companion object {
        fun instance(): DataMuseRepository =
            DataMuseRepositoryImpl(DataMuseRetrofit().retrofit())
    }
}