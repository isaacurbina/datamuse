package urbina.isaac.dictionary.service.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import urbina.isaac.dictionary.model.APIResponse

interface DataMuseService {

    @GET("words")
    suspend fun search(
        @Query("ml") query: String
    ): List<APIResponse?>?
}