import libraly.contract.ElectronicSignature
import libraly.data.HashDataRule
import utils.HashUtils

class SignatureLibraly<T : HashDataRule>(
        private val cipher: ElectronicSignature<T>
) {

    fun singAllMessage(messages: ByteArray): List<T> {
        println(cipher.name)
        println("Генерируем значения..")
        cipher.generate()
        println("Проверяем сгенерированные значения..")
        cipher.checkRule()
        println("Начинаем подпись ключей..")
        return HashUtils.sha256(messages)
                .map { cipher.sign(it) }
    }

    fun verifyAllMessage(hash: List<T>): Boolean {
        println("Верификация..")
        return hash.map { cipher.verify(it) }
                .find { !it } ?: false
    }
}