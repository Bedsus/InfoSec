import libraly.contract.ElectronicSignature
import libraly.data.HashDataRule

class SignatureLibrary<T : HashDataRule>(
        private val cipher: ElectronicSignature<T>
) {

    fun singAllMessage(messages: ByteArray): List<T> {
        println(cipher.name)
        println("Генерируем значения..")
        cipher.generate()
        println("Проверяем сгенерированные значения..")
        cipher.checkRule()
        println("Начинаем подпись ключей..")
        return messages.map { cipher.sign(it) }
    }

    fun verifyAllMessage(hash: List<T>): Boolean {
        println("Верификация..")
        val verify = hash.map { cipher.verify(it) }
        return verify.none { !it }
    }
}