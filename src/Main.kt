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
    getImageToBynary()
}


fun getImageToBynary() {
   // try {
        val fi = File("src/res/android.jpg")
        val bytes: ByteArray = Files.readAllBytes(fi.toPath())
        val list = mutableListOf<Byte>()
        for(element in bytes) list.add(element)
        val listBytesArrays = list.chunked(Long.SIZE_BYTES)
        /*val intBuf = ByteBuffer.wrap(fileContent)
            .order(ByteOrder.BIG_ENDIAN)
            .asLongBuffer()
        val array = LongArray(intBuf.remaining())
        intBuf.get(array)
        //println(intBuf.toString())
        println(array.toString())*/
        println(ByteUtils.bytesToLong(bytes))

        FileOutputStream("src/res/1.jpg").use { stream -> stream.write(bytes) }
    // } catch(ex: Exception) {
   //     println("Ошибка чтения файла!")
   //     return
    //}
}