package anonimys

import anonimys.contract.Server
import libraly.RsaCipher

@ExperimentalUnsignedTypes
class ServerImpl : Server {

    private val rsa = RsaCipher()

    init {
        rsa.generate()
    }

    override fun giveBulletin(key: Long): Long {
        return rsa.encrypt(key)
    }

    override fun pickBulletin(bulletin: Long) {

    }
}