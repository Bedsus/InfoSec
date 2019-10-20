
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files


@ExperimentalUnsignedTypes
fun main() {
    val library = LibraryManager()
    //library.powsRandom()
    //library.extendedEuclideanRandom()
    //library.hellmanRandom()
    //library.babyStepGiantStepRandom()
    //library.shamir()
    val result = getImageToBynary()
    arrayLongToImage(result)
}


fun getImageToBynary(): List<Long> {
    val fi = File("src/res/android.jpg")
    val bytes: ByteArray = Files.readAllBytes(fi.toPath())
    val list = mutableListOf<Byte>()
    for(element in bytes) list.add(element)
    val listBytesArrays = list.chunked(Long.SIZE_BYTES)
    val listLong: MutableList<Long> = mutableListOf()
    for (array in listBytesArrays) {
        var byte = array.toByteArray()
        if (byte.size < 8) {
            val temp = ByteArray(Long.SIZE_BYTES)
            for (b in 0 until Long.SIZE_BYTES) {
                temp[b] = byte.getOrNull(b) ?: 0
            }
            byte = temp
        }
        listLong.add(ByteUtils.bytesToLong(byte))
    }
    return listLong
}

fun arrayLongToImage(list: List<Long>) {
    val bytes = mutableListOf<ByteArray>()
    for (i in list) {
        bytes.add(ByteUtils.longToBytes(i))
    }
    FileOutputStream("src/res/2.jpg").use { stream ->
        for(b in bytes)
        stream.write(b)
    }
}