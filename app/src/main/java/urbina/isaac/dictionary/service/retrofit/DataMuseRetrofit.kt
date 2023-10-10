package urbina.isaac.dictionary.service.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataMuseRetrofit {

    fun retrofit(): DataMuseService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DataMuseService::class.java)

    companion object {
        private const val BASE_URL = "https://api.datamuse.com/"
    }
}
