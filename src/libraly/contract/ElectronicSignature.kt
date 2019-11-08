package libraly.contract

import libraly.data.HashDataRule

interface ElectronicSignature<M, T : HashDataRule> : Cipher {
    /**
     * Отправитель производить подпись документа
     */
    fun sign(m: M): T

    /**
     * Проверка подлиности подписи
     */
    fun verify(data: T): Boolean
}