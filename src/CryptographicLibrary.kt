import kotlin.math.pow


@ExperimentalUnsignedTypes
class CryptographicLibrary {

    /** Функция быстрого возведения числа [a1] в степень [b1] по модулю [m] */
    fun pows(a1: ULong, b1: ULong, m: ULong): ULong {
        var a: ULong = a1
        var b: ULong = b1
        var res = 1uL
        a = a % m
        while (b > 0uL) {
            if (b and 1uL == 1uL)
                res = res * a % m
            b = b shr 1
            a = a * a % m
        }
        return res
    }

    /** Функция быстрого возведения числа [a1] в степень [b1] по модулю [m] */
    fun pows(a1: Long, b1: Long, m: Long): Long {
        var a: Long = a1
        var b: Long = b1
        var res = 1L
        a %= m
        while (b > 0L) {
            if (b and 1L == 1L)
                res = res * a % m
            b = b shr 1
            a = a * a % m
        }
        return res
    }

    /** Функция быстрого возведения числа [a1] в степень [b1] c множетелем [x] по модулю [m] */
    fun pows(a1: ULong, b1: ULong, m: ULong, x: ULong): ULong {
        return (pows(a1, b1, m) * x % m) % m
    }

    /** Функция быстрого возведения числа [a1] в степень [b1] c множетелем [x] по модулю [m] */
    fun pows(a1: Long, b1: Long, m: Long, x: Long): Long {
        return (pows(a1, b1, m) * x % m) % m
    }

    fun isPrime(p: Int): Boolean {
        if(p <= 1) return false
        val b = p.toDouble().pow(0.5).toInt()
        for (i in 2..b) {
            if (p % i == 0) return false
        }
        return true
    }

    /** Алгоритм Евклида нахождения НОД двух чисел*/
    fun euclidean(a: ULong, b: ULong): ULong {
        return when (b) {
            0uL -> a
            else -> euclidean(b, a % b)
        }
    }

    /** Алгоритм Евклида нахождения НОД двух чисел*/
    fun euclidean(a: Long, b: Long): Long {
        return when (b) {
            0L -> a
            else -> euclidean(b, a % b)
        }
    }

    fun euclidean(a: Int, b: Int): Int {
        return when (b) {
            0 -> a
            else -> euclidean(b, a % b)
        }
    }

    /** Расширенный (обобщенный) алгоритм Евклида [ax + by = gcd(a,b)] */
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

    /**
     * Функция построения общего ключа для двух абонентов и по схеме Диффи-Хеллмана
     *
     * @param p простое натуральное число
     * @param g простое число
     * @param xa закрытый ключ абонента A
     * @param xb закрытый ключ абонента B
     *
     * @return секретный общий ключ
     */
    fun hellman(p: ULong, g: ULong, xa: ULong, xb: ULong): ULong {
        val q = (p - 1uL) / 2uL
        check(isPrime(q.toInt())) { "По алгоритму Ферма число q = $q должно быть простым!" }
        if(!isPrime(p.toInt())) println("По алгоритму Ферма число p = $p должно быть простым!")
        check(g in 1uL..p-1uL) { "Нарушено условие '1 < g < p'! [g = $g, p = $p]" }
        check(pows(g, q, p) != 1uL) { "Нарушено условие 'g ^ q mod p != 1' - число g должно быть первообразной корня по модулю p! [g = $g, q = $q, p = $p] " }
        val ya = pows(g, xa, p)
        val yb = pows(g, xb, p)
        println("Ya = $ya, Yb = $yb")
        val zab = pows(yb, xa, p)
        val zba = pows(ya, xb, p)
        check(zab == zba) { "У Алисы и Боба разные секретные ключи! Zab = $zab и Zba = $zba" }
        return zab
    }

    /**
     * Алгоритм Гельфонда — Шенкса (Шаг младенца - шаг великана)
     * Нахождение степени x числа [a] по модулю [p] равное числу [y]
     */
    fun babyStepGiantStep(a: ULong, p: ULong, y: ULong, m: ULong): ULong {
        check(y < p) { "Нарушено условие 'y < p'! [y = $y, p = $p]" }
        check(m * m > p) { "Нарушено условие 'm * k > p'! [m = $m, p = $p]" }
        val jMap = mutableMapOf<ULong, ULong>()
        for (j in 0uL..(m - 1uL)) {
            val aj = pows(a, j, p, y)
            jMap[aj] = j
        }
        for (i in 1uL..m) {
            val ai = pows(a, i * m, p)
            val j: ULong? = jMap[ai]
            if (j != null) {
                val x = i * m - j
                val check = pows(a, x, p)
                check(check == y ) { "Неверное решение: y_res $check != y_ans $y" }
                return x
            }
        }
        throw IllegalStateException("Число X не найденно!")
    }
}