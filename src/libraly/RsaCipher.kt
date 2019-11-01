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
    var publicKey: Pair<Long, Long> = Pair(3L, 9173503L)
    var privateKey: Pair<Long, Long> = Pair(6111579L, 9173503L)

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

    override fun sign(m: Byte): RsaHashData {
        val (c, n) = privateKey
        val h = HashUtils.sha256(m)
        val s = library.pows(h.toLong(), c, n)
        return RsaHashData(m, s)
    }

    override fun verify(data: RsaHashData): Boolean {
        val (d, n) = publicKey
        val m = data.m
        val s = data.s
        val h = HashUtils.sha256(m)
        val e = library.pows(s, d, n).toByte()
        return e == h
    }
}