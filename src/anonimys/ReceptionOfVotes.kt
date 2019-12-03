package anonimys

import anonimys.contract.Client
import anonimys.contract.Server


/**
 * Класс приемной голосов. Приемная имеет сервер с которым работают голосующие клиенты
 */
@ExperimentalUnsignedTypes
class ReceptionOfVotes(countClient: Int) {

    private val server: Server = ServerImpl()
    private val clients = mutableListOf<Client>()

    init {
        for (id in 1..countClient) {
           clients.add(ClientImpl(id, server))
        }
    }

    fun holdVote() {
        clients.forEach { it.toVote() }
        server.showResult()
    }

}