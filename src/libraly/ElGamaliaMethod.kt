package libraly

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
class ElGamaliaMethod : EncryptionScheme<Pair<Long, Long>>() {

    private var p = 0L
    private var g = 0L
    private var x = 0L
    private var y = 0L
    private var k = 0L

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

    override fun encrypt(message: Long): Pair<Long, Long> {
        check(message < p) {
            "Сообщение должно быть меньше чем [message = $message, p = $p]"
        }
        val a = library.pows(g, k, p)
        val b = library.pows(y, k, p, message)
        return Pair(a, b)
    }

    override fun decrypt(message: Pair<Long, Long>): Long {
        return library.pows(message.first, p - 1L - x, p, message.second)
    }
}