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
}