package utils

import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

object FileUtils {

    fun fileToLongList(name: String): MutableList<Long> {
        println("Считываем файл..")
        val file = File("src/res/$name")
        val bytes: ByteArray = Files.readAllBytes(file.toPath())
        val list = mutableListOf<Long>()
        for(element in bytes)
            list.add(element.toLong())
        return list
    }

    fun longListToFile(name: String, list: List<Long>) {
        println("Создаем файл '$name'..")
        val bytes = ByteArray(list.size)
        for (i in list.indices) {
            bytes[i] = list[i].toByte()
        }
        FileOutputStream("src/res/$name").use { stream -> stream.write(bytes) }
    }
}