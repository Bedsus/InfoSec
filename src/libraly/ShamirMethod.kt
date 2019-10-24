package libraly

import CryptographicLibrary
import utils.RandomUtils

/**
 * Алгоритм Шамира
 *
 * [p] заведомо большое простое число
 * [e] число, которое выбрал абонент A
 * [d] число, которое выбрал абонент A
 * [c] число, которое выбрал абонент B
 * [b] число, которое выбрал абонент B
 *
 * , Шифр Вернама, Шифр RSA
 */
@ExperimentalUnsignedTypes
class ShamirMethod : EncryptionScheme<Long, Long> {

    private val library = CryptographicLibrary()
    private var p = 0L
    private var e = 0L
    private var d = 0L
    private var c = 0L
    private var b = 0L

    override fun generate() {
        p = RandomUtils.getPrimeNumber()
        e = RandomUtils.getMutuallyPrime(p - 1)
        d = RandomUtils.getRandomSh2(p,e)
        c = RandomUtils.getMutuallyPrime(p - 1)
        b = RandomUtils.getRandomSh2(p,c)
    }

    override fun checkRule() {
        check( library.euclidean(e, p-1L) == 1L ) {
            "Число e=$e должно быть взаимно простое с p-1 = ${p-1L}"
        }
        check( ((e % (p - 1L)) * (d % (p - 1L))) % (p - 1L) == 1L ) {
            "Нарушено условие '(d*e) mod (p-1) = 1'! [d = $d, e = $e, p = $p]"
        }
    }

    override fun encrypt(message: Long): Long {
        val ae = library.pows(message, e, p)
        return library.pows(ae, b, p)
    }

    override fun decrypt(message: Long): Long {
        val ae = library.pows(message, d, p)
        return library.pows(ae, c, p)
    }
}
