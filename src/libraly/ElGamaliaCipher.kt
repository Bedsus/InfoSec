package libraly

import CryptographicLibrary
import libraly.contract.ElectronicSignature
import libraly.contract.EncryptionCipher
import libraly.data.ElGamaliaHashData
import utils.HashUtils
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
class ElGamaliaCipher : EncryptionCipher<Long, Pair<Long, Long>>, ElectronicSignature<ElGamaliaHashData> {

    private val library = CryptographicLibrary()
    private var p = 0L
    private var g = 0L
    private var x = 0L
    private var y = 0L
    private var k = 0L

    override val name = "Шифр Эль-Гамаля"

    override fun generate() {
        val (p1, _) = RandomUtils.getPQ()
        p = p1.toLong()
        x = RandomUtils.getAntiderivative(p)
        g = RandomUtils.getAntiderivative(p)
        k = RandomUtils.getAntiderivative(p)
        y = library.pows(g, x, p)
    }

    override fun checkRule() {
        check(library.isPrime(p.toInt())) {
            "По алгоритму Ферма число p = $p должно быть простым!"
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

    override fun signByte(m: Byte, h: Long): ElGamaliaHashData {
        return signLong(m.toLong(), h)
    }

    override fun verifyByte(data: ElGamaliaHashData, h: Long): Boolean {
       return verifyLong(data, h)
    }

    override fun signLong(m: Long, h: Long): ElGamaliaHashData {
        check(h in 1 until p) {
            "Сообщение должно быть меньше чем [message = $h, p = $p]"
        }
        k = RandomUtils.getMutuallyPrime(p-1)
        val data = library.extendedEuclidean(k, p-1)
        var kInv = data.y
        if (kInv < 1) kInv += p - 1
        check((k * kInv) % (p - 1L) == 1L) { "Нарушено условие" }
        val r  = library.pows(g, k, p)
        var u  = (h - x * r) % (p - 1)
        if (u < 1)
            u += p - 1
        val s = (kInv * u) % (p - 1)
        return ElGamaliaHashData(m.toLong(), r, s, y, p, g)
    }

    override fun verifyLong(data: ElGamaliaHashData, h: Long): Boolean {
        data.apply {
            val x1 = library.pows(y, r, p)
            val x2 = library.pows(r, s, p)
            val x3 = (x1 * x2) % p
            val x4 = library.pows(g, h, p)
            return x3 == x4
        }
    }
}