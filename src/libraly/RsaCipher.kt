package libraly

import CryptographicLibrary
import utils.RandomUtils

@ExperimentalUnsignedTypes
class RsaCipher : EncryptionCipher<Long, Long>, HashCipher<Long, Pair<Long, Long>> {

    private val library = CryptographicLibrary()
    var publicKey: Pair<Long, Long> = Pair(3L, 9173503L)
    var privateKey: Pair<Long, Long> = Pair(6111579L, 9173503L)

    override val name = "Шифр RSA"

    override fun generate() {
        // TODO Ошибка в генерации ключей !!
        val p = RandomUtils.getShortPrimeNumber()
        println("p = $p")
        val q = RandomUtils.getShortPrimeNumber()
        println("q = $q")
        val n = p * q // модуль
        println("n = $n")
        val f = (p - 1L) * (q - 1L) // функция Эйлера
        println("f = $f")
        val e = RandomUtils.getMutuallyPrime(f) // простая из чисел Ферма
        println("e = $e")
        /** val e = RandomUtils.getMutuallyPrime(f) // открытая экспонента */
        val d = RandomUtils.getMultiplicativelyInverse(f, e) // Секретная экспонента
        println("d = $d")
        publicKey = Pair(e, n)
        privateKey = Pair(d, n)
    }

    override fun hash(message: Long): Pair<Long, Long> {
        val (d, n) = privateKey
        val h = SHA.hash(message.toString())
        val s = library.pows(0L/*h*/, d, n) // TODO h string to long
        return Pair(message, s)
    }

    override fun verification(message: Pair<Long, Long>): Boolean {
        val (m, s) = message
        val (e, n) = publicKey
        val h = SHA.hash(m.toString())
        val E = library.pows(s, e, n)
        return E == 0L // TODO h string to long
    }

    override fun checkRule() {

    }

    override fun encrypt(m: Long): Long {
        val (e, n) = publicKey
        return library.pows(m, e, n)
    }

    override fun decrypt(m: Long): Long {
        val (d, n) = privateKey
        return library.pows(m, d, n)
    }
}