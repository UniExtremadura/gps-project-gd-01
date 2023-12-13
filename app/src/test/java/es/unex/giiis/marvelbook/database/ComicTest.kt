package es.unex.giiis.marvelbook.database

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class ComicTest {
    private lateinit var comic: Comic

    @Before
    fun setUp() {
        comic = Comic(
            id = 1,
            title = "Spiderman",
            description = "Spiderman",
            isbn = "123456789",
            format = "comic",
            pageCount = 100,
            imagen = "imagen",
            creators = listOf("Stan Lee"),
            characters = listOf("Spiderman")
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getId() {
        assertEquals(1, comic.id)
    }

    @Test
    fun getTitle() {
        assertEquals("Spiderman", comic.title)
    }

    @Test
    fun getDescription() {
        assertEquals("Spiderman", comic.description)
    }

    @Test
    fun getIsbn() {
        assertEquals("123456789", comic.isbn)
    }

    @Test
    fun getFormat() {
        assertEquals("comic", comic.format)
    }

    @Test
    fun getPageCount() {
        assertEquals(100, comic.pageCount)
    }

    @Test
    fun getImagen() {
        assertEquals("imagen", comic.imagen)
    }

    @Test
    fun getCreators() {
        assertEquals(listOf("Stan Lee"), comic.creators)
    }

    @Test
    fun getCharacters() {
        assertEquals(listOf("Spiderman"), comic.characters)
    }
}