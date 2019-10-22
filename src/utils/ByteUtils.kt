package utils
import java.nio.ByteBuffer


object ByteUtils {

    const val size = Short.SIZE_BYTES

    fun longToBytes(x: Long): ByteArray {
        val buffer = ByteBuffer.allocate(Long.SIZE_BYTES)
        buffer.putLong(0, x)
        return buffer.array()
    }

    fun bytesToLong(bytes: ByteArray): Long {
        val buffer = ByteBuffer.allocate(Long.SIZE_BYTES)
        buffer.put(bytes, 0, bytes.size)
        buffer.flip()//need flip
        return buffer.long
    }

    fun intToBytes(x: Int): ByteArray {
        val buffer = ByteBuffer.allocate(Int.SIZE_BYTES)
        buffer.putInt(0, x)
        return buffer.array()
    }

    fun bytesToInt(bytes: ByteArray): Int {
        val buffer = ByteBuffer.allocate(Int.SIZE_BYTES)
        buffer.put(bytes, 0, bytes.size)
        buffer.flip()//need flip
        return buffer.int
    }

    fun shortToBytes(x: Short): ByteArray {
        val buffer = ByteBuffer.allocate(Short.SIZE_BYTES)
        buffer.putShort(0, x)
        return buffer.array()
    }

    fun bytesToShort(bytes: ByteArray): Short {
        val buffer = ByteBuffer.allocate(Short.SIZE_BYTES)
        buffer.put(bytes, 0, bytes.size)
        buffer.flip()//need flip
        return buffer.short
    }
}