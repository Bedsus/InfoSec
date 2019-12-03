package anonimys.contract

interface Server {
    fun showResult()
    val publicKey: Pair<Long, Long>
    fun giveBulletin(id: Int, H: Long): Long
    fun pickBulletin(n: Long, s: Long)
}