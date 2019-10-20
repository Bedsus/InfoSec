
import java.nio.ByteBuffer


object ByteUtils {

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
}