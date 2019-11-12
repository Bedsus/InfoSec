package libraly

import CryptographicLibrary
import libraly.contract.ElectronicSignature
import libraly.data.GostHashData
import utils.HashUtils
import utils.RandomUtils

@ExperimentalUnsignedTypes
class GostElectronicSignature : ElectronicSignature<Byte, GostHashData> {

    override val name = "ГОСТ Р34.10-9"
    private val library = CryptographicLibrary()

    private var p = 0L
    private var q = 0L
    private var a = 0L
    private var x = 0L
    private var y = 0L

    override fun generate() {
        val (pInt, qInt) = RandomUtils.getPQ()
        p = pInt.toLong()
        q = qInt.toLong()
        val g = RandomUtils.getNumber()
        a = library.pows(g, (p-1)/q, p)
        x = RandomUtils.getAntiderivative(q)
        y = library.pows(a, x, p)
    }

    override fun checkRule() {
        check(library.isPrime(p.toInt())) { "Число p = $p должно быть простым!" }
        check(library.isPrime(q.toInt())) { "Число q = $q должно быть простым!" }
        check( library.pows(a, q, p) == 1L) { "Нарушено условие: a^q mod p = 1" }
    }


    override fun sign(m: Byte): GostHashData {
        val h = HashUtils.sha256Int(m)
        check(h in 1 until q) {
            "Сообщение должно быть меньше чем [message = $h, p = $p]"
        }
        var k: Long
        var r: Long
        var s: Long
        do {
            k = RandomUtils.getMutuallyPrime(p-1)
            r = library.pows(a, k, p) % q
            s = (k * h + x * r) % q
        } while (r == 0L || s == 0L)
        return GostHashData(m, r , s)
    }

    override fun verify(data: GostHashData): Boolean {
        data.apply {
            val h = HashUtils.sha256Int(m)
            check(r in 1 until q) {
                "0 < r = $r < q = $q"
            }
            check(s in 1 until q) {
                "0 < r = $s < q = $q"
            }
            val eucl = library.extendedEuclidean(h.toLong(), q)
            var hInv = eucl.y
            if (hInv < 1) hInv += p - 1
            val u1 = (s * hInv) % q
            var u2 = (-r * hInv) % q
            if (u2 < 1) u2 += q
            val v1 = library.pows(a, u1, p)
            val v2 = library.pows(y, u2, p)
            val v = ((v1 * v2) % p) % q
            return v == r
        }
    }
}