package utils

import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

object ImageUtils {

    fun imageToLongList(name: String): MutableList<Long> {
        println("Считываем файл..")
        val file = File("src/res/$name.jpg")
        val bytes: ByteArray = Files.readAllBytes(file.toPath())
        val list = mutableListOf<Byte>()
        for(element in bytes)
            list.add(element)
        val listBytesArrays = list.chunked(ByteUtils.size)
        val listLong: MutableList<Long> = mutableListOf()
        for (array in listBytesArrays) {
            var byte = array.toByteArray()
            if (byte.size < ByteUtils.size) {
                val temp = ByteArray(ByteUtils.size)
                for (b in 0 until ByteUtils.size) {
                    temp[b] = byte.getOrNull(b) ?: 0
                }
                byte = temp
            }
            listLong.add(ByteUtils.bytesToShort(byte).toLong())
        }
        return listLong
    }

    fun longListToImage(name: String, list: List<Long>) {
        println("Создаем файл '$name'..")
        val bytes = mutableListOf<ByteArray>()
        for (i in list) {
            bytes.add(ByteUtils.shortToBytes(i.toShort()))
        }
        FileOutputStream("src/res/$name.jpg").use { stream ->
            for(b in bytes)
                stream.write(b)
        }
    }
}