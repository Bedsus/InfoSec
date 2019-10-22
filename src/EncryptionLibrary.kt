@ExperimentalUnsignedTypes
class EncryptionLibrary {

    private val library = CryptographicLibrary()

    /**
     * Алгоритм Шамира
     * @param m сообщение, которое нужно зашифровать
     * @param p заведомо большое число
     * @param e число, которое выбрал абонент A
     * @param d число, которое выбрал абонент A
     * @param c число, которое выбрал абонент B
     * @param b число, которое выбрал абонент B
     */
    fun shamir(m: ULong, p: ULong, e: ULong, d: ULong, c: ULong, b: ULong): ULong {
        check( library.euclidean(e, p-1uL) == 1uL ) {
            "Число e=$e должно быть взаимно простое с p-1 = ${p-1uL}"
        }
        // de = 1 mod (p-1) -> de = 1+n(p-1)
        check( ((e % (p - 1uL)) * (d % (p - 1uL))) % (p - 1uL) == 1uL ) {
            "Нарушено условие '(d*e) mod (p-1) = 1'! [d = $d, e = $e, p = $p]"
        }
        val ae = library.pows(m, e, p) // A шифрует сообщение, передает В
        println(ae)
        val be = library.pows(ae, b, p) // В получил зашифрованное сообщение, преобразует и передает А
        println(be)
        val ad = library.pows(be, d, p) // А получает шифр, преобразует и передает В
        println(ad)
        return library.pows(ad, c, p) // В получает шифр и расшифровывает сообщение
    }


}