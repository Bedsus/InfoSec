import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalUnsignedTypes
class CryptographicLibraryTest {

    private val library = CryptographicLibrary()

    @Test
    @Throws(Exception::class)
    fun `Modular Exponentiation of small numbers №1`() {
        val x = library.pows(56uL, 4uL, 11uL)
        assertEquals(x, 1uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Modular Exponentiation of small numbers №2`() {
        val x = library.pows(10uL, 12uL, 28uL)
        assertEquals(x, 8uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Modular Exponentiation of small numbers №3`() {
        val x = library.pows(2uL, 256uL, 28uL)
        assertEquals(x, 16uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Modular Exponentiation of big numbers №1`() {
        val x = library.pows(26732uL, 25634uL, 3228uL)
        assertEquals(x, 244uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Modular Exponentiation of max numbers №1`() {
        val x = library.pows(18446744073709551615uL, 18446744073709551615uL, 18446744073709551615uL)
        assertEquals(x, 0uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Modular Exponentiation of max numbers №2`() {
        val x = library.pows(18446744073709551615uL, 2uL, 88uL)
        assertEquals(x, 49uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Euclidean of small numbers №1`() {
        val x = library.euclidean(30uL, 18uL)
        assertEquals(x, 6uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Euclidean of small numbers №2`() {
        val x = library.euclidean(60uL, 45uL)
        assertEquals(x, 15uL)
    }


    @Test
    @Throws(Exception::class)
    fun `Euclidean of small numbers №3`() {
        val x = library.euclidean(127765uL, 102212uL)
        assertEquals(x, 25553uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Euclidean of max numbers №1`() {
        val x = library.euclidean(18446744073709551615uL, 18446744073709551615uL)
        assertEquals(x, 18446744073709551615uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Euclidean of max numbers №2`() {
        val x = library.euclidean(18446744073709551615uL, 18446744073709551600uL)
        assertEquals(x, 15uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Ferma of max numbers №1`() {
        val x = library.ferma(18446744073709551615uL)
        assertTrue(!x)
    }
}