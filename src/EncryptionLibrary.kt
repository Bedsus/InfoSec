import libraly.EncryptionScheme

@ExperimentalUnsignedTypes
class EncryptionLibrary<T>(
        private val encryptionScheme: EncryptionScheme<T>
) {

    fun encryptAllMessage(messages: List<Long>): List<T> {
        println("Генерируем значения..")
        encryptionScheme.generate()
        println("Проверяем сгенерированные значения..")
        encryptionScheme.checkRule()
        println("Начинаем шифрование..")
        return messages.map { encryptionScheme.encrypt(it) }
    }

    fun decryptAllMessage(messages: List<T>): List<Long> {
        println("Начинаем расшифровку..")
        return messages.map { encryptionScheme.decrypt(it) }
    }
}