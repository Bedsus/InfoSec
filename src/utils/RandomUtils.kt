package utils

import CryptographicLibrary
import kotlin.random.Random

@ExperimentalUnsignedTypes
object RandomUtils {

    private val library = CryptographicLibrary()
    private const val MAX_RANDOM_RANGE = 1000000000 //10 ^ 9
    const val MIN_RANDOM_RANGE = 1

    /** Рандомное число до 10^9 */
    private val randomNumber: Int
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

    fun getNumber(): Long {
        return randomNumber.toLong()
    }

    fun getPrimeNumber(): Long {
        return randomPrimeNumber.toLong()
    }

    fun getShortPrimeNumber(): Long {
        var x: Int
        do {
            x = Random.nextInt(MIN_RANDOM_RANGE, Short.MAX_VALUE.toInt())
        } while (!library.isPrime(x))
        return x.toLong()
    }

    /**
     * Получение числа мультипликативно обратное к числу [e] по модулю [p], такое что: d * e = 1 mod p
     */
    fun getMultiplicativelyInverse(e: Long, p: Long): Long {
        var d: Long
        do {
            d = (1L + getNumber() * p) / e
        } while (((e % p) * (d % p)) % p != 1L)
        return d
    }

    /**
     * Получаем первообразную
     */
    fun getAntiderivative(p: Long): Long {
        return Random.nextLong(1L, p - 1L)
    }
}