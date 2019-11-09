package utils

import java.security.MessageDigest

object HashUtils {

    fun sha512(input: ByteArray) = hash("SHA-512", input)

    fun sha256(input: ByteArray) = hash("SHA-256", input)

   // fun sha256List(input: ByteArray) = hashInt("SHA-256", input)

    fun sha256Int(input: Byte) = hashInt("SHA-256", input)

    fun sha1(input: ByteArray) = hash("SHA-1", input)

    private fun hash(type: String, message: ByteArray) = MessageDigest.getInstance(type).digest(message)

   /* private fun hashInt(type: String, message: ByteArray): List<Int>{
        val bytes: ByteArray = MessageDigest.getInstance(type).digest(message)
        val list: MutableList<Int> = mutableListOf()
        for(i in 0..bytes.size){
            list[i] = (bytes[i] + 128)
        }
        return list
    }*/

    private fun hashInt(type: String, message: Byte): Int{
        val bytes = ByteArray(1)
        bytes[0] = message
        val bytesHash = MessageDigest.getInstance(type).digest(bytes)
        return  bytesHash[0] + 128
    }

    fun sha256(input: Byte) = hash("SHA-256", input)

    private fun hash(type: String, message: Byte): Byte {
        val bytes = ByteArray(1)
        bytes[0] = message
        return MessageDigest.getInstance(type).digest(bytes)[0]
    }
}