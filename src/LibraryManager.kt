import utils.RandomUtils
import utils.RandomUtils.MIN_RANDOM_RANGE
import kotlin.math.sqrt
import kotlin.random.Random

@ExperimentalUnsignedTypes
class LibraryManager {

    private val library = CryptographicLibrary()

    companion object {
        val whiteColor = 27.toChar() + "[30m"
        val violetColor = 27.toChar() + "[35m"
        val yellowColor = 27.toChar() + "[32m"
    }

    fun powsRandom() {
        val a = RandomUtils.randomPrimeNumber.toULong()
        val b = RandomUtils.randomPrimeNumber.toULong()
        val m = RandomUtils.randomPrimeNumber.toULong()
        val x = library.pows(a, b, m)
        println("""|
            |${violetColor}1. Функция быстрого возведения:$whiteColor
            |$a ^ $b mod $m = ${yellowColor}$x$whiteColor""".trimMargin())
    }

    fun extendedEuclideanRandom() {
        val a = RandomUtils.getNumber()
        val b = RandomUtils.getNumber()
        val res = library.extendedEuclidean(a, b)
        println("""|
            |${violetColor}2. Расширенный (обобщенный) алгоритм Евклида:$whiteColor
            |a = $a, b = $b 
            |${yellowColor}d = ${res.d}, x = ${res.x}, y = ${res.y}$whiteColor""".trimMargin())
    }

    fun hellmanRandom() {
        val (p, q) = RandomUtils.getPQ()
        val g = Random.nextInt(MIN_RANDOM_RANGE, p)
        val xa = RandomUtils.getNumber().toULong()
        val xb = RandomUtils.getNumber().toULong()
        println("""|
            |${violetColor}3. Функция построения общего ключа для двух абонентов и по схеме Диффи-Хеллмана:$whiteColor
            |p = $p, q = $q, g = $g, Xa = $xa, Xb = $xb""".trimMargin())
        val zab = library.hellman(p.toULong(), g.toULong(), xa, xb)
        println("${yellowColor}Алиса и Боб получили общий секретный ключ! K = $zab$whiteColor")

    }

    fun babyStepGiantStepRandom() {
        val a = RandomUtils.randomPrimeNumber.toULong()
        val x1 = RandomUtils.randomPrimeNumber.toULong()
        val p = RandomUtils.randomPrimeNumber.toULong()
        val y = library.pows(a, x1, p)
        val m = sqrt((p).toDouble()).toULong() + 1uL
        println("""|
            |${violetColor}4. Алгоритм Гельфонда — Шенкса (Шаг младенца - шаг великана):$whiteColor
            |$a ^ x mod $p = $y, m = $m""".trimMargin())
        val x = library.babyStepGiantStep(a, p.toULong(), y, m)
        println("${yellowColor}x = $x$whiteColor")
    }
}