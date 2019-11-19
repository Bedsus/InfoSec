package libraly

import CryptographicLibrary
import libraly.contract.ElectronicSignature
import libraly.contract.EncryptionCipher
import libraly.data.RsaHashData
import utils.HashUtils
import utils.RandomUtils

@ExperimentalUnsignedTypes
class RsaCipher : EncryptionCipher<Long, Long>, ElectronicSignature<RsaHashData> {

    private val library = CryptographicLibrary()
    private var publicKey: Pair<Long, Long> = Pair(3L, 9173503L)
    private var privateKey: Pair<Long, Long> = Pair(6111579L, 9173503L)

    override val name = "Шифр RSA"

    override fun generate() {
        val p = RandomUtils.getShortPrimeNumber()
        println("p = $p")
        val q = RandomUtils.getShortPrimeNumber()
        println("q = $q")
        val n = p * q // модуль
        println("n = $n")
        val f = (p - 1L) * (q - 1L) // функция Эйлера
        println("f = $f")
        val e = RandomUtils.getMutuallyPrime(f) // открытая экспонента, простая из чисел Ферма
        println("e = $e")
        val d = RandomUtils.getMultiplicativelyInverse(e, f) // Секретная экспонента
        println("d = $d")
        publicKey = Pair(e, n)
        privateKey = Pair(d, n)
    }

    override fun checkRule() { }

    override fun encrypt(m: Long): Long {
        val (e, n) = publicKey
        return library.pows(m, e, n)
    }

    override fun decrypt(m: Long): Long {
        val (d, n) = privateKey
        return library.pows(m, d, n)
    }

    override fun signByte(m: Byte, h: Long): RsaHashData {
        return signLong(m.toLong(), h)
    }

    override fun verifyByte(data: RsaHashData, h: Long): Boolean {
        return verifyLong(data, h)
    }

    override fun signLong(m: Long, h: Long): RsaHashData {
        val (c, n) = privateKey
        val s = library.pows(h, c, n)
        return RsaHashData(m, s)
    }

    override fun verifyLong(data: RsaHashData, h: Long): Boolean {
        val (d, n) = publicKey
        val s = data.s
        val e = library.pows(s, d, n)
        return e == h
    }
}