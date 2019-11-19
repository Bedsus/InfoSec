package utils
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

    fun bytesToLongList(bytes: ByteArray): MutableList<Long> {
        val list = mutableListOf<Byte>()
        for (element in bytes)
            list.add(element)
        val listBytesArrays = list.chunked(Long.SIZE_BYTES)
        val listLong: MutableList<Long> = mutableListOf()
        for (array in listBytesArrays) {
            var byte = array.toByteArray()
            if (byte.size < Long.SIZE_BYTES) {
                val temp = ByteArray(Long.SIZE_BYTES)
                for (b in 0 until Long.SIZE_BYTES) {
                    temp[b] = byte.getOrNull(b) ?: 0
                }
                byte = temp
            }
            listLong.add(bytesToLong(byte))
        }
        return listLong
    }
}