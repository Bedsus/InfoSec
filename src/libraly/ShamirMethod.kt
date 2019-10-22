package libraly

import CryptographicLibrary
import utils.RandomUtils

/**
 * Алгоритм Шамира
 * @param message сообщение, которое нужно зашифровать
 * [p] заведомо большое простое число
 * [e] число, которое выбрал абонент A
 * [d] число, которое выбрал абонент A
 * [c] число, которое выбрал абонент B
 * [b] число, которое выбрал абонент B
 *
 * Шифр Эль-Гамаля, Шифр Вернама, Шифр RSA
 */
@ExperimentalUnsignedTypes
class ShamirMethod(private var message: MutableList<Long>) {

    private val library = CryptographicLibrary()

    private val p = RandomUtils.randomPrimeNumber.toLong()
    private val e = RandomUtils.getMutuallyPrime(p.toInt() - 1).toLong()
    private val d = getRandomSh2(p,e)
    private val c = RandomUtils.getMutuallyPrime(p.toInt() - 1).toLong()
    private val b = getRandomSh2(p,c)

    init {
        check( library.euclidean(e, p-1L) == 1L ) {
            "Число e=$e должно быть взаимно простое с p-1 = ${p-1L}"
        }
        check( ((e % (p - 1L)) * (d % (p - 1L))) % (p - 1L) == 1L ) {
            "Нарушено условие '(d*e) mod (p-1) = 1'! [d = $d, e = $e, p = $p]"
        }
    }

    private fun getRandomSh2(p: Long, e: Long): Long {
        var x: Long
        do {
            x = (1L + RandomUtils.randomNumber.toLong() * (p - 1L)) / e
        } while (((e % (p - 1L)) * (x % (p - 1L))) % (p - 1L) != 1L)
        return x
    }

    private fun step1(num: Long) = library.pows(num, e, p) // A шифрует сообщение, передает В
    private fun step2(num: Long) = library.pows(num, b, p) // В получил зашифрованное сообщение, преобразует и передает А
    private fun step3(num: Long) = library.pows(num, d, p) // А получает шифр, преобразует и передает В
    private fun step4(num: Long) = library.pows(num, c, p) // В получает шифр и расшифровывает сообщение

    fun shamir(): MutableList<Long> {
        return message.map {
            val ae = step1(it)
            val be = step2(ae)
            val ad = step3(be)
            step4(ad)
        }.toMutableList()
    }

    private val shamirSteps: Iterator<(Long) -> Long> = mutableListOf<(Long) -> Long>(
        { step1(it) },
        { step2(it) },
        { step3(it) },
        { step4(it) }
    ).iterator()

    fun nextStep(): MutableList<Long> {
        if (shamirSteps.hasNext()) {
            val step: (Long) -> Long = shamirSteps.next()
            message = message.map { step(it) }.toMutableList()
        }
        return message
    }
}
