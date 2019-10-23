package utils

import CryptographicLibrary
import kotlin.random.Random

@ExperimentalUnsignedTypes
object RandomUtils {

    private val library = CryptographicLibrary()
    private const val MAX_RANDOM_RANGE = 1000000000 //10 ^ 9
    const val MIN_RANDOM_RANGE = 1

    /** Рандомное число до 10^9 */
    val randomNumber: Int
        get() = Random.nextInt(MIN_RANDOM_RANGE, MAX_RANDOM_RANGE)

    val randomPrimeNumber: Int
        get() {
            var x: Int
            do {
                x = randomNumber
            } while (!library.isPrime(x))
            return x
        }

    fun getMutuallyPrime(x: Long): Long {
        var e: Long
        do {
            e = Random.nextLong(1L, x)
        } while (library.euclidean(e, x) != 1L)
        return e
    }

    fun getPrimeNumber(): Long {
        return randomPrimeNumber.toLong()
    }

    fun getRandomSh2(p: Long, e: Long): Long {
        var x: Long
        do {
            x = (1L + RandomUtils.randomNumber.toLong() * (p - 1L)) / e
        } while (((e % (p - 1L)) * (x % (p - 1L))) % (p - 1L) != 1L)
        return x
    }

    /**
     * Получаем первообразную
     */
    fun getAntiderivative(p: Long): Long {
        return Random.nextLong(1L, p - 1L)
    }
}