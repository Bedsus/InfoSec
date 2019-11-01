package utils

import java.security.MessageDigest

object HashUtils {

    fun sha512(input: ByteArray) = hash("SHA-512", input)

    fun sha256(input: ByteArray) = hash("SHA-256", input)

    fun sha1(input: ByteArray) = hash("SHA-1", input)

    private fun hash(type: String, message: ByteArray) = MessageDigest.getInstance(type).digest(message)

    fun sha256(input: Byte) = hash("SHA-256", input)

    private fun hash(type: String, message: Byte): Byte {
        val bytes = ByteArray(1)
        bytes[0] = message
        return MessageDigest.getInstance(type).digest(bytes)[0]
    }
}