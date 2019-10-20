
import java.nio.ByteBuffer


object ByteUtils {
    private val buffer = ByteBuffer.allocate(Long.SIZE_BYTES)

    fun longToBytes(x: Long): ByteArray {
        buffer.putLong(0, x)
        return buffer.array()
    }

    fun bytesToLong(bytes: ByteArray): Long {
        buffer.put(bytes, 0, bytes.size)
        buffer.flip()//need flip
        return buffer.long
    }
}