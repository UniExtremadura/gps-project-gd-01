package es.unex.giiis.marvelbook.api

import com.google.gson.Gson
import es.unex.giiis.marvelbook.data.api.PersonajeCabecera
import es.unex.giiis.marvelbook.data.api.PersonajeDatos
import es.unex.giiis.marvelbook.data.api.PersonajeMarvel
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
    fun getPersonajesTest() = runBlocking {

        val ejemploPersonajeMarvel = PersonajeMarvel(
            id = 1,
            name = "Spider-Man",
            description = "The amazing Spider-Man!",
            modified = "2023-01-01T12:00:00Z",
            thumbnail = null,
            resourceURI = null,
            comics = null,
            series = null,
            stories = null,
            events = null,
            urls = arrayListOf()
        )

        val listaPersonajesMarvel = ArrayList<PersonajeMarvel>()
        listaPersonajesMarvel.add(ejemploPersonajeMarvel)

        val ejemploPersonajeDatos = PersonajeDatos(
            offset = 0,
            limit = 1,
            total = 1,
            count = 1,
            results = listaPersonajesMarvel
        )

        val ejemploPersonajeCabecera = PersonajeCabecera(
            code = 200,
            status = "OK",
            copyright = "Copyright © 2023 Marvel",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href=\"http://marvel.com\">Data provided by Marvel. © 2023 MARVEL</a>",
            etag = "W/\"123456789\"",
            data = ejemploPersonajeDatos
        )
        val gson = Gson()

        val jsonString = gson.toJson(ejemploPersonajeCabecera)

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

        val response: PersonajeCabecera = service.getPersonajes(20)

        TestCase.assertTrue(response.data!!.results.size == 1)

        mockWebServer.shutdown()
    }


    @Test
    @Throws(IOException::class)
    fun getEmptyPersonajesTest() = runBlocking {

        val ejemploPersonajeDatos = PersonajeDatos(
            offset = 0,
            limit = 1,
            total = 1,
            count = 1,
            results = ArrayList<PersonajeMarvel>()
        )

        val ejemploPersonajeCabecera = PersonajeCabecera(
            code = 200,
            status = "OK",
            copyright = "Copyright © 2023 Marvel",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href=\"http://marvel.com\">Data provided by Marvel. © 2023 MARVEL</a>",
            etag = "W/\"123456789\"",
            data = ejemploPersonajeDatos
        )
        val gson = Gson()

        val jsonString = gson.toJson(ejemploPersonajeCabecera)

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

        val response: PersonajeCabecera = service.getPersonajes(20)

        TestCase.assertTrue(response.data!!.results.size == 0)

        mockWebServer.shutdown()
    }
}