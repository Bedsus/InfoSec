
import libraly.ElGamaliaCipher
import utils.FileUtils

@ExperimentalUnsignedTypes
fun main() {
    val data = FileUtils.fileToLongList("android.jpg")
    val method = ElGamaliaCipher()
    //val method = RsaCipher()
    val library = EncryptionLibrary(method)
    val encrypt = library.encryptAllMessage(data)
    //FileUtils.longListToFile("encrypt.jpg", encrypt)
    val result = library.decryptAllMessage(encrypt)
    FileUtils.longListToFile("decrypt.jpg", result)
}