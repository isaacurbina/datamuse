package urbina.isaac.dictionary.service.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataMuseRetrofit {

    fun retrofit(): DataMuseService =
        Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DataMuseService::class.java)

    private fun getLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.HEADERS)

    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .build()

    companion object {
        private const val BASE_URL = "https://api.datamuse.com/"
    }
}
