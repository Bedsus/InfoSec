package anonimys.contract

interface Server {
    fun giveBulletin(key: Long) : Long
    fun pickBulletin(bulletin: Long)

}