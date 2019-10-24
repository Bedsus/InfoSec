package libraly

/**
 * Общие правила для схемы щифрования, где:
 * [M] - тип сообщения
 * [T] - Тип зашифрованных данных
 */
@ExperimentalUnsignedTypes
interface EncryptionScheme<M, T> {

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
     * @param message сообщение
     * @return результат шифрования
     */
    fun encrypt(message: M): T

    /**
     * Расшифровка сообщения
     * @param message зашифрованное сообщение
     * @return результат расшифровки
     */
    fun decrypt(message: T): M
}