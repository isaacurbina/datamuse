package urbina.isaac.dictionary.repository

import urbina.isaac.dictionary.model.APIResponse
import urbina.isaac.dictionary.service.retrofit.DataMuseRetrofit

interface DataMuseRepository {

    suspend fun search(query: String, page: Int): List<APIResponse?>?

    companion object {
        fun instance(): DataMuseRepository =
            DataMuseRepositoryImpl(DataMuseRetrofit().retrofit())
    }
}