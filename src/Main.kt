
import libraly.ShamirMethod
import utils.ImageUtils


@ExperimentalUnsignedTypes
fun main() {
    println("Считываем файл..")
    val data = ImageUtils.imageToLongList("android")
    println("Генерируем и проверяем значения..")
    val shamir = ShamirMethod(data)
    println("Начинаем расчет..")
    //val result = shamir.shamir()
    for (i in 1..4) {
        val result = shamir.nextStep()
        ImageUtils.longListToImage(i.toString(), result)
    }
    //println("Расчет закончен. Создаем файл..")
   // ImageUtils.longListToImage("11", result)
    println("Файл создан!")

}