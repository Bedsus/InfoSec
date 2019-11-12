package poker

import utils.RandomUtils
import CryptographicLibrary

@ExperimentalUnsignedTypes
class Player(private val p: Long) {

    private val library = CryptographicLibrary()
    private val deckInHandsEncrypt = mutableListOf<Long>()
    private val deckInHands = mutableListOf<Long>()

    private var ca: Long = 0
    private var da: Long = 0

    init {
        ca = RandomUtils.getMutuallyPrime(p - 1L)
        val data = library.extendedEuclidean(ca, p - 1L)
        da = (p - 1L + data.y) % (p - 1L)
        check((ca * da) % (p - 1L) == 1L) { "Нарушено условие: (ca * da) % (p - 1) == 1" }
    }

    /**
     * Генерация уникальных номеров карт дял всей колоды
     * Выполняет один из игроков и возвращает колоду
     */
    fun generateDeck(deckNames: List<String>): Map<Long, String> {
        var res: Map<Long, String>
        do {
            res = deckNames.map { name ->
                RandomUtils.getMutuallyPrime(p - 1L) to name
            }.toMap()
            val countAllCart = res.keys.size
            val countUniqueCart = res.keys.toMutableList()
                    .distinct().size
        } while (countAllCart != countUniqueCart)
        return res
    }

    /**
     * Зашифровываем колоду карт и перемешиваем её
     */
    fun encryptDeck(desk: List<Long>): List<Long> {
        return desk.map { library.pows(it, ca, p) }.shuffled()
    }

    /**
     * Расшифровываем колоду карт и возвращаем её
     */
    fun decryptDeck(deck: List<Long>): List<Long> {
        return deck.map { library.pows(it, da, p) }
    }

    /**
     * Передать свою зашифрованную колоду
     */
    fun getDeckInHandsEncrypt(): List<Long> {
        return deckInHandsEncrypt
    }

    /**
     * Получаем расшифрованную колоду другими игроками, расшифровываем её сами и кладем в руку
     */
    fun setLastDeck(desk: List<Long>) {
        val decryptDesk = decryptDeck(desk)
        deckInHands.addAll(decryptDesk)
    }

    /**
     * Взять одну зашифрованную карту из колоды и вернуть обратно
     */
    fun getEncryptCart(deck: List<Long>, countCart: Int = 1): List<Long> {
        val shuffledDeck = deck.shuffled().toMutableList()  // перетусовать
        deckInHandsEncrypt.addAll(shuffledDeck.take(countCart)) // взять карты
        shuffledDeck.removeAll(deckInHandsEncrypt) // убрать карты из колоды
        return shuffledDeck
    }

    fun showDeckInHand(deckMap: Map<Long, String>) {
        deckInHands.forEach {
            println(deckMap[it])
        }
    }
}
