
import anonimys.ReceptionOfVotes

@ExperimentalUnsignedTypes
fun main() {
    /*   val data = FileUtils.fileToByteArray("data.txt")
     // val method = ElGamaliaCipher()
     // val method = RsaCipher()
      val method = GostElectronicSignature()
     /* val signLib = SignatureLibrary(method)
     // val hash = signLib.singAllMessage(data.toList())
      val bytes = FileUtils.imageToLongList("data.txt")
      val hash = signLib.singAllMessage(bytes[0])
      signLib.verifyAllMessage(hash)*/

      val library = SignatureLibrary(method)
      val encrypt = library.singAllMessage(data)
      //FileUtils.longListToFile("encrypt.jpg", encrypt)
      library.verifyAllMessage(encrypt)
  */

    ReceptionOfVotes(10).holdVote()
  //  MentalPoker().distributionCards(10)
}