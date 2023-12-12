package es.unex.giiis.marvelbook.api

import es.unex.giiis.marvelbook.data.api.ComicCabecera
import es.unex.giiis.marvelbook.data.api.CreadorCabecera
import es.unex.giiis.marvelbook.data.api.PersonajeCabecera
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val service: MarvelAPI by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://gateway.marvel.com/v1/public/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(MarvelAPI::class.java)
}

fun getNetworkService() = service

interface MarvelAPI {

    @GET("comics?ts=1&apikey=320e1b5bed8c8bdb3aa5366857f05023&hash=6bcb692a16cd638fd8dad21766b4fac7")
    suspend fun getComics(
        @Query("offset") offset: Int
    ): ComicCabecera

    @GET("characters?ts=1&apikey=320e1b5bed8c8bdb3aa5366857f05023&hash=6bcb692a16cd638fd8dad21766b4fac7")
    suspend fun getPersonajes(
        @Query("offset") offset: Int
    ): PersonajeCabecera

    @GET("creators?ts=1&apikey=320e1b5bed8c8bdb3aa5366857f05023&hash=6bcb692a16cd638fd8dad21766b4fac7")
    suspend fun getCreadores(
        @Query("offset") offset: Int
    ): CreadorCabecera

}

class APIError(message: String, cause: Throwable?) : Throwable(message, cause)