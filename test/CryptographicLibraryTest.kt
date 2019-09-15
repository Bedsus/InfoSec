import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalUnsignedTypes
class CryptographicLibraryTest {

    private val library = CryptographicLibrary()

    // Modular Exponentiation ------------------------

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

    // Euclidean ---------------------------------

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

    // Ferma -----------------------------------

    @Test
    @Throws(Exception::class)
    fun `Ferma of small numbers №1`() {
        val x = library.ferma(17uL)
        assertTrue(x)
    }

    @Test
    @Throws(Exception::class)
    fun `Ferma of small numbers №2`() {
        val x = library.ferma(199uL)
        assertTrue(x)
    }


    @Test
    @Throws(Exception::class)
    fun `Ferma of small numbers №3`() {
        val x = library.ferma(3571uL)
        assertTrue(x)
    }

    @Test
    @Throws(Exception::class)
    fun `Ferma of small numbers №4`() {
        val x = library.ferma(115249uL)
        assertTrue(x)
    }

    // Extended Euclidean ----------------------------

    @Test
    @Throws(Exception::class)
    fun `Extended Euclidean of small numbers №1`() {
        val res = library.extendedEuclidean(1550L, 150L)
        assertEquals(res.d, 50L)
        assertEquals(res.x, 1L)
        assertEquals(res.y, -10L)
    }

    @Test
    @Throws(Exception::class)
    fun `Extended Euclidean of small numbers №2`() {
        val res = library.extendedEuclidean(99999L, 999L)
        assertEquals(res.d, 9L)
        assertEquals(res.x, -10L)
        assertEquals(res.y, 1001L)
    }

    @Test
    @Throws(Exception::class)
    fun `Extended Euclidean of small numbers №3`() {
        val res = library.extendedEuclidean(42346L, 6546L)
        assertEquals(res.d, 2L)
        assertEquals(res.x, -1177L)
        assertEquals(res.y, 7614L)
    }

    @Test
    @Throws(Exception::class)
    fun `Extended Euclidean of max numbers №1`() {
        val res = library.extendedEuclidean(9223372036854775807L, 9223372036854775800L)
        assertEquals(res.d, 7L)
        assertEquals(res.x, 1L)
        assertEquals(res.y, -1L)
    }

    // Diffie-Hellman key ----------------------------

    @Test
    @Throws(Exception::class)
    fun `Diffie-Hellman key of small numbers №1`() {
        val k = library.hellman(11uL, 7uL, 6uL, 5uL)
        assertEquals(k, 1uL)
    }

    @Test
    @Throws(Exception::class)
    fun `Diffie-Hellman key of small numbers №2`() {
        val k = library.hellman(541uL, 10uL, 34uL, 86uL)
        assertEquals(k, 303uL)
    }


    @Test
    @Throws(Exception::class)
    fun `Diffie-Hellman key of small numbers №3`() {
        val k = library.hellman(115249uL, 56uL, 347uL, 863uL)
        assertEquals(k, 3668uL)
    }

    // Baby step - Giant step ----------------------------

    @Test
    @Throws(Exception::class)
    fun `Baby step - Giant step of small numbers №1`() {
        val k = library.babyStepGiantStep(11uL, 7uL, 6uL, 5uL)
        assertEquals(k, 15uL)
    }
}