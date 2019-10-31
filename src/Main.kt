
import libraly.RsaCipher
import utils.FileUtils

@ExperimentalUnsignedTypes
fun main() {
    val data = FileUtils.fileToByteArray("android.jpg")
    //val method = ElGamaliaCipher()
    val method = RsaCipher()
    val signLib = SignatureLibraly(method)
    val res = signLib.singAllMessage(data)
    println(signLib.verifyAllMessage(res))
    /*
    val library = EncryptionLibrary(method)
    val encrypt = library.encryptAllMessage(data)
    //FileUtils.longListToFile("encrypt.jpg", encrypt)
    val result = library.decryptAllMessage(encrypt)
    FileUtils.longListToFile("decrypt.jpg", result)
    */
}