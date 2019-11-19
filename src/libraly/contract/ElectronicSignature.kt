package libraly.contract

import libraly.data.HashDataRule

interface ElectronicSignature<T : HashDataRule> : Cipher {
    /**
     * Отправитель производить подпись документа
     */
    fun signByte(m: Byte, h: Long): T

    /**
     * Проверка подлиности подписи
     */
    fun verifyByte(data: T, h: Long): Boolean

    /**
     * Отправитель производить подпись документа
     */
    fun signLong(m: Long, h: Long): T

    /**
     * Проверка подлиности подписи
     */
    fun verifyLong(data: T, h: Long): Boolean
}