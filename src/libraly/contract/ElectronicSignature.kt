package libraly.contract

import libraly.data.HashDataRule

interface ElectronicSignature<T : HashDataRule> : Cipher {
    /**
     * Отправитель производить подпись документа
     */
    fun sign(m: Byte): T

    /**
     * Проверка подлиности подписи
     */
    fun verify(data: T): Boolean
}