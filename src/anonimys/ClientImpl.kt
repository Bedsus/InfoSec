package anonimys

import CryptographicLibrary
import anonimys.contract.Client
import anonimys.contract.Server
import utils.HashUtils
import utils.RandomUtils
import kotlin.random.Random

@ExperimentalUnsignedTypes
class ClientImpl(
        private val id: Int,
        private val server: Server
) : Client {

    private val library = CryptographicLibrary()

    override fun toVote() {
        val (d, N) = server.publicKey
        val rnd = Random.nextInt(1, 1000)
        // TODO Помимо голоса может быть другая информация
        val vote =  Random.nextInt(0,2)
        val n = "$rnd$vote".toLong()
        val r = RandomUtils.getMutuallyPrime(N)
        val h = HashUtils.sha(n)
        var h1 = library.pows(r, d, N, h)
        if (h1 < 0) h1 += N
        check(h1 < N) { "Значение N слишком мало! hash = $h1, N = $N" }
        val s1 = server.giveBulletin(id, h1)
        val rInv = RandomUtils.getMultiplicativelyInverse(s1, N)
        val s = ((s1 % N) * (rInv % N)) % N
        server.pickBulletin(n, s)
    }
}