
@ExperimentalUnsignedTypes
fun main() {
    val library = CryptographicLibrary()
    val result = library.pows(56uL, 4uL, 11uL)
    println("x = $result")

    val p = 11uL
    val g = 7uL
    val xa = 6uL
    val xb = 5uL
    library.hellman(p, g, xa, xb)

    val d = library.euclidean(18uL, 8uL)
    println("D = $d")
    val a = 155L
    val b = 134L
    val res = library.extendedEuclidean(a, b)
    println("a = $a, b = $b, d = ${res.d}, x = ${res.x}, y = ${res.y}")

    val x = library.babyStepGiantStep(2uL, 23uL, 9uL, 6uL)
    println("x = $x")
}