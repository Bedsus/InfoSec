import java.lang.IllegalStateException
import kotlin.random.Random
import kotlin.random.nextULong

@ExperimentalUnsignedTypes
class CryptographicLibrary {

    /** Функция быстрого возведения числа [a] в степень [b] c множетелем [x] по модулю [m] */
    fun pows(a: ULong, b: ULong, m: ULong, x: ULong = 1uL): ULong {
        if (b == 0uL) return 1uL
        val t = pows(a, b / 2uL, m)
        val t1 = if (b.isEven())
            t else (a * t)
        return (t1 * t * x) % m
    }

    /** Алгоритм Евклида нахождения НОД двух чисел*/
    fun euclidean(a: ULong, b: ULong): ULong {
        return when (b) {
            0uL -> a
            else -> euclidean(b, a % b)
        }
    }

    /** Расширенный (обобщенный) алгоритм Евклида */
    fun extendedEuclidean(a: Long, b: Long): GcdExtData {
        return if (b > a) gcdExt(b , a) else gcdExt(a , b)
    }

    /** Результаты расчета расширенного алгоритма Евклида */
    class GcdExtData(var d: Long, var x: Long, var y: Long)

    private fun gcdExt(a: Long, b: Long): GcdExtData {
        if (b == 0L) {
            return GcdExtData(a, 1L, 0L)
        }
        val res = gcdExt(b, a % b)
        val s = res.y
        res.y = res.x - (a / b) * res.y
        res.x = s
        return res
    }

    /** Тест Ферма на простоту числа */
    fun ferma(x: ULong): Boolean {
        if (x == 2uL) return true
        for (i in 0..100) {
            val a = (Random.nextULong() % (x - 2uL)) + 2uL
            when {
                euclidean(a, x) != 1uL -> return false
                pows(a, x - 1uL, x) != 1uL -> return false
            }
        }
        return true
    }

    /** Проверка на четность числа */
    private fun ULong.isEven() = (this % 2uL == 0uL)

    /**
     * Функция построения общего ключа для двух абонентов и по схеме Диффи-Хеллмана
     *
     * @param p простое натуральное число
     * @param g простое число
     * @param xa закрытый ключ абонента A
     * @param xb закрытый ключ абонента B
     */
    fun hellman(p: ULong, g: ULong, xa: ULong, xb: ULong) {
        val q = (p - 1uL) / 2uL
        check(ferma(p) && ferma(q)) { "По алгоритму Ферма числа p = $p и q = $q должны быть простыми!" }
        check(g in 1uL..p-1uL) { "Нарушено условие '1 < g < p'! [g = $g, p = $p]" }
        val ya = pows(g, xa, p)
        val yb = pows(g, xb, p)
        val zab = pows(yb, xa, p)
        val zba = pows(ya, xb, p)
        check(zab == zba) { "У Алисы и Боба разные секретные ключи! Zab = $zab и Zba = $zba" }
        println("Алиса и Боб получили общий секретный ключ! K = $zab, Ya = $ya, Yb = $yb")
    }

    /**
     * Алгоритм Гельфонда — Шенкса (Шаг младенца - шаг великана)
     * Нахождение степени x числа [a] по модулю [p] равное числу [y]
     */
    fun babyStepGiantStep(a: ULong, p: ULong, y: ULong, m: ULong, k: ULong): ULong {
        check(y < p) { "Нарушено условие 'y < p'! [y = $y, p = $p]" }
        check(m * k > p) { "Нарушено условие 'm * k > p'! [m = $m, k = $k, p = $p]" }
        val jList = mutableMapOf<ULong, ULong>()
        for (j in 0uL..(m - 1uL)) {
            val ai = pows(a, j, p, y)
            jList[ai] = j
        }
        for (i in 1uL..m) {
            val ai = pows(a, i * m, p)
            val aj: ULong? = jList[ai]
            aj?.let {
                return i * m - it
            }
        }
        throw IllegalStateException("Число X не найденно!")
    }
}