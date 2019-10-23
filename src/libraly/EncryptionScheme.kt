package libraly

import CryptographicLibrary
/**
 * Общие правила для схемы щифрования
 */
@ExperimentalUnsignedTypes
abstract class EncryptionScheme<T> {

    protected val library = CryptographicLibrary()
    /**
     * Генерация случайных чисел для шифрования
     */
    abstract fun generate()

    /**
     * Проверяем удовлетворяют
     */
    abstract fun checkRule()

    /**
     * Шифрование сообщения
     * @param message сообщение
     * @return результат шифрования
     */
    abstract fun encrypt(message: Long): T

    /**
     * Расшифровка сообщения
     * @param message сообщение
     * @return результат расшифровки
     */
    abstract fun decrypt(message: T): Long
}