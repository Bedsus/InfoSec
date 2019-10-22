
import libraly.ShamirMethod
import utils.ImageUtils


@ExperimentalUnsignedTypes
fun main() {
    val result = ImageUtils.imageToLongList("android")
    val shamir = ShamirMethod(result)
    shamir.generate()
    ImageUtils.longListToImage(
        "11",
        shamir.shamir()
    )
        /*
    ImageUtils.longListToImage(
        "1",
        shamir.step1()
    )
    ImageUtils.longListToImage(
        "2",
        shamir.step2()
    )
    ImageUtils.longListToImage(
        "3",
        shamir.step3()
    )
    ImageUtils.longListToImage(
        "4",
        shamir.step4()
    )*/
}