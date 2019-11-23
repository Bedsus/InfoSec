package utils

import java.security.MessageDigest

object HashUtils {

    fun sha256(input: Byte): Long {
        val bytes = ByteArray(1)
        bytes[0] = input
        val bytesHash = sha256digest(bytes)
        return  bytesHash[0] + 128L
    }

    fun sha256(message: Long): Long {
        val bytes = ByteUtils.longToBytes(message)
        val bytesHash = sha256digest(bytes)
        return ByteUtils.bytesToLongList(bytesHash)[0]
    }

    fun sha256digest(bytes: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-256")
                .digest(bytes)
    }
}