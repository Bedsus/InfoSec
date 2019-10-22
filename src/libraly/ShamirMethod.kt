package libraly

import CryptographicLibrary
import utils.RandomUtils
import utils.RandomUtils.MIN_RANDOM_RANGE
import kotlin.random.Random

/**
 * Алгоритм Шамира
 * @param message сообщение, которое нужно зашифровать
 * @param p заведомо большое число
 * @param e число, которое выбрал абонент A
 * @param d число, которое выбрал абонент A
 * @param c число, которое выбрал абонент B
 * @param b число, которое выбрал абонент B
 *
 * Шифр Эль-Гамаля, Шифр Вернама, Шифр RSA
 */
@ExperimentalUnsignedTypes
class ShamirMethod(
    private var message: MutableList<Long>,
    private var p: Long = 0L,
    private var e: Long = 0L,
    private var d: Long = 0L,
    private var c: Long = 0L,
    private var b: Long = 0L
) {

    private val library = CryptographicLibrary()

    fun generate() {
        p = RandomUtils.randomPrimeNumber.toLong()
        e = getRandomSh1(p.toInt()).toLong()
        d = getRandomSh2(p,e)
        c = getRandomSh1(p.toInt()).toLong()
        b = getRandomSh2(p,c)
    }

    fun check() {
        check( library.euclidean(e, p-1L) == 1L ) {
            "Число e=$e должно быть взаимно простое с p-1 = ${p-1L}"
        }
        // de = 1 mod (p-1) -> de = 1+n(p-1)
        check( ((e % (p - 1L)) * (d % (p - 1L))) % (p - 1L) == 1L ) {
            "Нарушено условие '(d*e) mod (p-1) = 1'! [d = $d, e = $e, p = $p]"
        }
    }

    fun shamir(): MutableList<Long> {
        check()
        return message.map {
            val ae = library.pows(it, e, p) // A шифрует сообщение, передает В
            val be = library.pows(ae, b, p) // В получил зашифрованное сообщение, преобразует и передает А
            val ad = library.pows(be, d, p) // А получает шифр, преобразует и передает В
            val r = library.pows(ad, c, p)  // В получает шифр и расшифровывает сообщение
            r
        }.toMutableList()
    }

    private val shamirSteps: Iterator<(Long) -> Long> = mutableListOf<(Long) -> Long>(
        { num -> library.pows(num, e, p) },
        { num -> library.pows(num, b, p) },
        { num -> library.pows(num, d, p) },
        { num -> library.pows(num, c, p) }
    ).iterator()

    fun nextStep(): MutableList<Long> {
        if (shamirSteps.hasNext()) {
            val step: (Long) -> Long = shamirSteps.next()
            message = message.map { step(it) }
                .toMutableList()
        }
        return message
    }

    private fun getRandomSh1(p: Int): Int {
        var e: Int
        do {
            e = Random.nextInt(MIN_RANDOM_RANGE, p)
        } while (library.euclidean(e.toULong(), (p-1).toULong()) != 1uL)
        return e
    }

    private fun getRandomSh2(p: Long, e: Long): Long {
        var x: Long
        do {
            x = (1L + RandomUtils.randomNumber.toLong() * (p - 1L)) / e
        } while (((e % (p - 1L)) * (x % (p - 1L))) % (p - 1L) != 1L)
        return x
    }

}
