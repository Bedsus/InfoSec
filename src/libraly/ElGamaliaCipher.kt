package libraly

import CryptographicLibrary
import utils.RandomUtils

/**
 * Шифр Эль-Гамаля
 *
 * [p] случайное простое число
 * [g] целое число, первообразный корень [p]
 * [x] закрытый ключ
 * [y] открытый ключ
 */
@ExperimentalUnsignedTypes
class ElGamaliaCipher : EncryptionCipher<Long, Pair<Long, Long>> {

    private val library = CryptographicLibrary()
    private var p = 0L
    private var g = 0L
    private var x = 0L
    private var y = 0L
    private var k = 0L

    override val name = "Шифр Эль-Гамаля"

    override fun generate() {
        p = RandomUtils.getPrimeNumber()
        x = RandomUtils.getAntiderivative(p)
        g = RandomUtils.getAntiderivative(p)
        k = RandomUtils.getAntiderivative(p)
        y = library.pows(g, x, p)
    }

    override fun checkRule() {
        check(library.isPrime(p.toInt())) {
            "По алгоритму Ферма число q = $p должно быть простым!"
        }
        check(x in 1L until p) {
            "Нарушено условие '1 < x < p-1'! [x = $x, p = $p]"
        }
        check(k in 1L until p) {
            "Нарушено условие '1 < k < p-1'! [k = $k, p = $p]"
        }
        check(library.pows(g, x, p) != 1L) {
            "Нарушено условие 'g ^ x mod p != 1' - число g должно быть первообразной корня по модулю p! [g = $g, x = $x, p = $p] "
        }
    }

    override fun encrypt(m: Long): Pair<Long, Long> {
        check(m < p) {
            "Сообщение должно быть меньше чем [message = $m, p = $p]"
        }
        val a = library.pows(g, k, p)
        val b = library.pows(y, k, p, m)
        return Pair(a, b)
    }

    override fun decrypt(m: Pair<Long, Long>): Long {
        val (a, b) = m
        return library.pows(a, p - 1L - x, p, b)
    }
}