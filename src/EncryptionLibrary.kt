import libraly.EncryptionScheme

@ExperimentalUnsignedTypes
class EncryptionLibrary<M, T>(
        private val encryptionScheme: EncryptionScheme<M, T>
) {

    fun encryptAllMessage(messages: List<M>): List<T> {
        println("Генерируем значения..")
        encryptionScheme.generate()
        println("Проверяем сгенерированные значения..")
        encryptionScheme.checkRule()
        println("Начинаем шифрование..")
        return messages.map { encryptionScheme.encrypt(it) }
    }

    fun decryptAllMessage(messages: List<T>): List<M> {
        println("Начинаем расшифровку..")
        return messages.map { encryptionScheme.decrypt(it) }
    }
}