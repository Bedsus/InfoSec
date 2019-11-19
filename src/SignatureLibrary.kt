import libraly.contract.ElectronicSignature
import libraly.data.HashDataRule
import utils.HashUtils

class SignatureLibrary<T : HashDataRule>(
        private val cipher: ElectronicSignature<T>
) {

    fun singAllMessage(messages: List<Byte>): List<T> {
        println(cipher.name)
        println("Генерируем значения..")
        cipher.generate()
        println("Проверяем сгенерированные значения..")
        cipher.checkRule()
        println("Начинаем подпись ключей..")
        return messages.map {
            cipher.signByte(it, HashUtils.sha256(it))
        }
    }

    fun verifyAllMessage(hash: List<T>) {
        println("Верификация..")
        val verify = hash.map { cipher.verifyByte(it, HashUtils.sha256(it.m.toByte())) }
        if (verify.none { !it }) {
            println("Верификация прошла успешно!")
        } else {
            println("Ошибка верификации")
        }
    }

    fun singAllMessage(messages: Long): T {
        println(cipher.name)
        println("Генерируем значения..")
        cipher.generate()
        println("Проверяем сгенерированные значения..")
        cipher.checkRule()
        println("Начинаем подпись ключей..")
        return cipher.signLong(messages, HashUtils.sha256(messages))
    }

    fun verifyAllMessage(hash: T) {
        println("Верификация..")
        if (cipher.verifyLong(hash, HashUtils.sha256(hash.m))) {
            println("Верификация прошла успешно!")
        } else {
            println("Ошибка верификации")
        }
    }
}