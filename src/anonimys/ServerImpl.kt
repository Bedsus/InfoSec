package anonimys

import CryptographicLibrary
import anonimys.contract.Server
import utils.HashUtils
import utils.RandomUtils

@ExperimentalUnsignedTypes
class ServerImpl : Server {

    private val result = mutableListOf<Int>()

    /**
     * Список людей, которым уже выдали билютени
     */
    private val voting = mutableListOf<Int>()
    override val publicKey: Pair<Long, Long>
    private val privateKey: Pair<Long, Long>
    private val library = CryptographicLibrary()

    init {
        val p = RandomUtils.getPrimeNumber()
        val q = RandomUtils.getPrimeNumber()
        val n = p * q // модуль
        val f = (p - 1L) * (q - 1L) // функция Эйлера
        val d = RandomUtils.getMutuallyPrime(f) // открытая экспонента, простая из чисел Ферма
        var c = library.extendedEuclidean(d, f).y
        if (c < 0) c += f
        publicKey = Pair(d, n)
        privateKey = Pair(c, n)
    }

    override fun giveBulletin(id: Int, H: Long): Long {
        check(!voting.contains(id)) { "Этому пользователю уже выдавали биллютень!" }
        voting.add(id)
        val (c, N) = privateKey
        return library.pows(H, c, N)
    }

    override fun pickBulletin(n: Long, s: Long) {
        val (d, N) = publicKey
        var h = HashUtils.sha(n) % N
        if (h < 0) h += N
        val verify = library.pows(s, d, N)
        check(h == verify) { "Билютеть не прошла проверку на корректность!" }
        val vote = (n % 10).toInt()
        result.add(vote)
    }

    override fun showResult(): List<String> {
        return result.map {
            when (it) {
                1 -> "Да"
                2 -> "Нет"
                else -> "Воздержался"
            }
        }
    }
}