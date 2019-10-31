package libraly

import libraly.contract.EncryptionCipher
import kotlin.random.Random

/**
 * Шифр Вернама
 * В качестве результата выступает пара значений: криптотекст и ключ
 */
@ExperimentalUnsignedTypes
class VernamCipher : EncryptionCipher<Long, Pair<Long, Long>> {

    override val name = "Шифр Вернама"

    override fun generate() {
        // Не используется
    }

    override fun checkRule() {
        // check(message.length() == key.length()){ "Ключ и сообщение должны быть одинакого размера" }
        // Не используется
    }

    override fun encrypt(m: Long): Pair<Long, Long> {
        val key = Random.nextLong(Long.MIN_VALUE,m - 1L)
        val ciphertext = key.xor(m)
        return Pair(ciphertext, key)
    }

    override fun decrypt(m: Pair<Long, Long>): Long {
        val (ciphertext, key) = m
        return ciphertext.xor(key)
    }

}