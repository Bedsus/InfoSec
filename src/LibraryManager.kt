import utils.RandomUtils
import utils.RandomUtils.MIN_RANDOM_RANGE
import kotlin.math.sqrt
import kotlin.random.Random

@ExperimentalUnsignedTypes
class LibraryManager {

    private val library = CryptographicLibrary()
    private val encryptionLibrary = EncryptionLibrary()

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
        val a = RandomUtils.randomNumber.toLong()
        val b = RandomUtils.randomNumber.toLong()
        val res = library.extendedEuclidean(a, b)
        println("""|
            |${violetColor}2. Расширенный (обобщенный) алгоритм Евклида:$whiteColor
            |a = $a, b = $b 
            |${yellowColor}d = ${res.d}, x = ${res.x}, y = ${res.y}$whiteColor""".trimMargin())
    }

    fun hellmanRandom() {
        var p: Int
        var q: Int
        do {
            p = RandomUtils.randomPrimeNumber
            q = (p - 1) / 2
        } while (!library.isPrime(q))
        val g = Random.nextInt(MIN_RANDOM_RANGE, p)
        val xa = RandomUtils.randomNumber.toULong()
        val xb = RandomUtils.randomNumber.toULong()
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

    fun shamir() {
        println("Начало расчета...")
        val m = 123454321uL
        val p = RandomUtils.randomPrimeNumber
        println("p = $p")
        val e = getRandomSh1(p)
        println("e = $e")
        val d = getRandomSh2(p.toULong(),e.toULong())
        println("d = $d")
        val c = getRandomSh1(p)
        println("c = $c")
        val b = getRandomSh2(p.toULong(),c.toULong())
        println("b = $b")
        val r = encryptionLibrary.shamir(
            m,
            p.toULong(),
            e.toULong(),
            d,
            c.toULong(),
            b
        )
        println("""|
            |${violetColor}1. Алгоритм Шамира:$whiteColor
            |m = $m, r = $r""".trimMargin())
    }

    private fun getRandomSh1(p: Int): Int {
        var e: Int
        do {
            e = Random.nextInt(MIN_RANDOM_RANGE, p)
        } while (library.euclidean(e.toULong(), (p-1).toULong()) != 1uL)
        return e
    }

    private fun getRandomSh2(p: ULong, e: ULong): ULong {
        var x: ULong
        do {
            x = (1uL + RandomUtils.randomNumber.toULong() * (p - 1uL)) / e
        } while (((e % (p - 1uL)) * (x % (p - 1uL))) % (p - 1uL) != 1uL)
        return x
    }
}