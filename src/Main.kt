
import libraly.ElGamaliaMethod
import utils.ImageUtils


@ExperimentalUnsignedTypes
fun main() {
    val data = ImageUtils.imageToLongList("android")
   // val method = ShamirMethod()
    val method = ElGamaliaMethod()
    val library = EncryptionLibrary(method)
    val encrypt = library.encryptAllMessage(data)
   // ImageUtils.longListToImage("encrypt", encrypt)
    val result = library.decryptAllMessage(encrypt)
    ImageUtils.longListToImage("decrypt", result)
}