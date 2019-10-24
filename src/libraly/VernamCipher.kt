package libraly

import kotlin.random.Random

/**
 * Шифр Вернама
 * В качестве
 */
@ExperimentalUnsignedTypes
class VernamCipher : EncryptionScheme<Long, Pair<Long, Long>> {

    override fun generate() {
        // Не используется
    }

    override fun checkRule() {
        // check(message.length() == key.length()){ "Ключ и сообщение должны быть одинакого размера" }
        // Не используется
    }

    override fun encrypt(message: Long): Pair<Long, Long> {
        val key = Random.nextLong(Long.MIN_VALUE,message - 1L)
        val ciphertext = key.xor(message)
        return Pair(ciphertext, key)
    }

    override fun decrypt(message: Pair<Long, Long>): Long {
        val (ciphertext, key) = message
        return ciphertext.xor(key)
    }

}