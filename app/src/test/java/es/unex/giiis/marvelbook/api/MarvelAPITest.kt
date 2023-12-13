package es.unex.giiis.marvelbook.api

import com.google.gson.Gson
import es.unex.giiis.marvelbook.data.api.ComicCabecera
import es.unex.giiis.marvelbook.data.api.ComicDatos
import es.unex.giiis.marvelbook.data.api.ComicMarvel
import es.unex.giiis.marvelbook.data.api.CreadorComic
import es.unex.giiis.marvelbook.data.api.CuentoComic
import es.unex.giiis.marvelbook.data.api.EventoComic
import es.unex.giiis.marvelbook.data.api.PersonajeComic
import es.unex.giiis.marvelbook.data.api.SerieComic
import es.unex.giiis.marvelbook.data.api.Thumbnail
import es.unex.giiis.marvelbook.data.api.PersonajeCabecera
import es.unex.giiis.marvelbook.data.api.PersonajeDatos
import es.unex.giiis.marvelbook.data.api.PersonajeMarvel
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

    @Test
    @Throws(IOException::class)
    fun getComicTest() = runBlocking {

        val ejemploComicMarvel = ComicMarvel(

            id = 1,
            digitalId = 123,
            title = "The Amazing Spider-Man",
            issueNumber = 1.0,
            variantDescription = "Variant Edition",
            description = "A thrilling comic adventure",
            modified = "2023-01-01",
            isbn = "1234567890",
            upc = "012345678901",
            diamondCode = "D123456",
            ean = "9876543210",
            issn = "1234-5678",
            format = "Comic",
            pageCount = 30,
            textObjects = arrayListOf(),
            resourceURI = "https://example.com/comic/1",
            urls = arrayListOf(),
            series = SerieComic(),
            variants = arrayListOf(),
            collections = arrayListOf(),
            collectedIssues = arrayListOf(),
            dates = arrayListOf(),
            prices = arrayListOf(),
            thumbnail = Thumbnail(),
            images = arrayListOf(),
            creators = CreadorComic(),
            characters = PersonajeComic(),
            stories = CuentoComic(),
            events = EventoComic()
        )



        val listaComicMarvel = ArrayList<ComicMarvel>()
        listaComicMarvel.add(ejemploComicMarvel)

        val ejemploComicDatos = ComicDatos(
            offset = 0,
            limit = 1,
            total = 1,
            count = 1,
            results = listaComicMarvel
        )

        val ejemploComicCabecera = ComicCabecera(
            code = 200,
            status = "OK",
            copyright = "Copyright © 2023 Marvel",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href=\"http://marvel.com\">Data provided by Marvel. © 2023 MARVEL</a>",
            etag = "W/\"123456789\"",
            data = ejemploComicDatos
        )
        val gson = Gson()

        val jsonString = gson.toJson(ejemploComicCabecera)

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

        val response: ComicCabecera = service.getComics(20)

        TestCase.assertTrue(response.data!!.results.size == 1)

        mockWebServer.shutdown()
    }


    @Test
    @Throws(IOException::class)
    fun getEmptyComicsTest() = runBlocking {

        val ejemploComicDatos = ComicDatos(
            offset = 0,
            limit = 1,
            total = 1,
            count = 1,
            results = ArrayList()
        )

        val ejemploComicCabecera = ComicCabecera(
            code = 200,
            status = "OK",
            copyright = "Copyright © 2023 Marvel",
            attributionText = "Data provided by Marvel. © 2023 MARVEL",
            attributionHTML = "<a href=\"http://marvel.com\">Data provided by Marvel. © 2023 MARVEL</a>",
            etag = "W/\"123456789\"",
            data = ejemploComicDatos
        )
        val gson = Gson()

        val jsonString = gson.toJson(ejemploComicCabecera)

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

        val response: ComicCabecera = service.getComics(20)

        TestCase.assertTrue(response.data!!.results.size == 0)

        mockWebServer.shutdown()
    }

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