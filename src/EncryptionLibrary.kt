import libraly.EncryptionCipher

@ExperimentalUnsignedTypes
class EncryptionLibrary<M, T>(
        private val cipher: EncryptionCipher<M, T>
) {

    fun encryptAllMessage(messages: List<M>): List<T> {
        println(cipher.name)
        println("Генерируем значения..")
        cipher.generate()
        println("Проверяем сгенерированные значения..")
        cipher.checkRule()
        println("Начинаем шифрование..")
        return messages.map { cipher.encrypt(it) }
    }

    fun decryptAllMessage(messages: List<T>): List<M> {
        println("Начинаем расшифровку..")
        return messages.map { cipher.decrypt(it) }
    }
}