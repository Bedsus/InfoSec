package libraly

/**
 * Общие правила для схемы щифрования, где:
 * [M] - тип сообщения
 * [T] - Тип зашифрованных данных
 */
@ExperimentalUnsignedTypes
interface EncryptionCipher<M, T> {

    val name: String

    /**
     * Генерация случайных чисел для шифрования
     */
    fun generate()

    /**
     * Проверяем удовлетворяют
     */
    fun checkRule()

    /**
     * Шифрование сообщения
     * @param m сообщение
     * @return результат шифрования
     */
    fun encrypt(m: M): T

    /**
     * Расшифровка сообщения
     * @param m зашифрованное сообщение
     * @return результат расшифровки
     */
    fun decrypt(m: T): M
}