
import libraly.ElGamaliaCipher
import utils.FileUtils

@ExperimentalUnsignedTypes
fun main() {
    val data = FileUtils.fileToCharArray("android.jpg")
    val method = ElGamaliaCipher()
    //val method = RsaCipher()
    val signLib = SignatureLibrary(method)
    val hash = signLib.singAllMessage(data.toList())
    if (signLib.verifyAllMessage(hash)){
        println("Верификация прошла успешно!")
    } else {
        println("Ошибка верификации")
    }
    /*
    val library = EncryptionLibrary(method)
    val encrypt = library.encryptAllMessage(data)
    //FileUtils.longListToFile("encrypt.jpg", encrypt)
    val result = library.decryptAllMessage(encrypt)
    FileUtils.longListToFile("decrypt.jpg", result)
    */
}