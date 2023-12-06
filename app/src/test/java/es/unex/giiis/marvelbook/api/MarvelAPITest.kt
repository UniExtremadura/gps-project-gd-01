package es.unex.giiis.marvelbook.api

import com.google.gson.Gson
import es.unex.giiis.marvelbook.data.api.CreadorCabecera
import es.unex.giiis.marvelbook.data.api.CreadorDatos
import es.unex.giiis.marvelbook.data.api.CreadorMarvel
import junit.framework.TestCase
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlinx.coroutines.runBlocking

class MarvelAPITest {

    @Test
    @Throws(IOException::class)
    fun getCreadoresTest() = runBlocking {

        val creador1 = CreadorMarvel(
            id = 1,
            firstName = "Stan",
            lastName = "Lee",
            fullName = "Stan Lee",
            thumbnail = null,
            resourceURI = null,
            comics = null,
            series = null,
            stories = null,
            events = null,
            urls = arrayListOf()
        )

        val creador2 = CreadorMarvel(
            id = 2,
            firstName = "Jack",
            lastName = "Kirby",
            fullName = "Jack Kirby",
            thumbnail = null,
            resourceURI = null,
            comics = null,
            series = null,
            stories = null,
            events = null,
            urls = arrayListOf()
        )

        val ejemploCreadorDatos = CreadorDatos(
            offset = 0,
            limit = 2,
            total = 2,
            count = 2,
            results = arrayListOf(creador1, creador2)
        )

        val ejemploCreadorCabecera = CreadorCabecera(
            code = 200,
            status = "OK",
            copyright = "Copyright © 2023 Marvel",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href=\"http://marvel.com\">Data provided by Marvel. © 2023 MARVEL</a>",
            etag = "W/\"123456789\"",
            data = ejemploCreadorDatos
        )

        val gson = Gson()

        val jsonString = gson.toJson(ejemploCreadorCabecera)

        val mockWebServer = MockWebServer()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody(jsonString)
        mockWebServer.enqueue(mockedResponse)

        val service: MarvelAPI = retrofit.create(MarvelAPI::class.java)

        val response: CreadorCabecera = service.getCreadores(20)

        TestCase.assertTrue(response.data!!.results.size == 2)

        mockWebServer.shutdown()
    }

    @Test
    @Throws(IOException::class)
    fun getEmptyCreadoresTest() = runBlocking {

        val ejemploCreadorDatos = CreadorDatos(
            offset = 0,
            limit = 2,
            total = 2,
            count = 2,
            results = arrayListOf()
        )

        val ejemploCreadorCabecera = CreadorCabecera(
            code = 200,
            status = "OK",
            copyright = "Copyright © 2023 Marvel",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href=\"http://marvel.com\">Data provided by Marvel. © 2023 MARVEL</a>",
            etag = "W/\"123456789\"",
            data = ejemploCreadorDatos
        )

        val gson = Gson()

        val jsonString = gson.toJson(ejemploCreadorCabecera)

        val mockWebServer = MockWebServer()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody(jsonString)
        mockWebServer.enqueue(mockedResponse)

        val service: MarvelAPI = retrofit.create(MarvelAPI::class.java)

        val response: CreadorCabecera = service.getCreadores(20)

        TestCase.assertTrue(response.data!!.results.size == 0)

        mockWebServer.shutdown()
    }
}