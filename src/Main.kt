
import libraly.ElGamaliaCipher
import libraly.GostElectronicSignature
import libraly.RsaCipher
import poker.MentalPoker
import utils.ByteUtils
import utils.FileUtils

@ExperimentalUnsignedTypes
fun main() {
  //  val data = FileUtils.fileToByteArray("data.txt")
  //  val method = ElGamaliaCipher()
  //  val method = RsaCipher()
    val method = GostElectronicSignature()
    val signLib = SignatureLibrary(method)
   // val hash = signLib.singAllMessage(data.toList())
    val bytes = FileUtils.imageToLongList("data.txt")
    val hash = signLib.singAllMessage(bytes[0])
    signLib.verifyAllMessage(hash)
    /*
    val library = EncryptionLibrary(method)
    val encrypt = library.encryptAllMessage(data)
    //FileUtils.longListToFile("encrypt.jpg", encrypt)
    val result = library.decryptAllMessage(encrypt)
    FileUtils.longListToFile("decrypt.jpg", result)
    */

  //  MentalPoker().distributionCards(10)
}