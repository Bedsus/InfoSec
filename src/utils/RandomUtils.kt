package utils

import CryptographicLibrary
import kotlin.random.Random

@ExperimentalUnsignedTypes
object RandomUtils {

    private val library = CryptographicLibrary()
    private const val MAX_RANDOM_RANGE = 1000000000 //10 ^ 9
    const val BIG_RANDOM_RANGE = 1000000 //10 ^ 6
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

    fun getMutuallyPrime(x: Int): Int {
        var e: Int
        do {
            e = Random.nextInt(MIN_RANDOM_RANGE, x)
        } while (library.euclidean(e.toLong(), x.toLong()) != 1L)
        return e
    }

    fun `(x*random) mod p = 1`(x: Long, p: Long): Long {
        var random: Long
        do {
            random = (1L + randomNumber.toLong() * (p - 1L)) / x
        } while (((x % (p - 1L)) * (random % (p - 1L))) % (p - 1L) != 1L)
        return random
    }

}