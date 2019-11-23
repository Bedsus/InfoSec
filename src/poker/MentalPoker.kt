package poker

import utils.RandomUtils


@ExperimentalUnsignedTypes
class MentalPoker {

    private val suits = listOf("пики", "крести", "черви", "буби")
    private val typeCart = listOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "валет", "дама", "король", "туз")
    private val deckNames = mutableListOf<String>()

    init {
        suits.forEach { suit ->
            typeCart.forEach { type -> deckNames.add("$type $suit") }
        }
    }

    private val countCart: Int
        get() = deckNames.size

    companion object{
        const val COUNT_CART_ON_TABLE = 5
    }

    fun distributionCards(countPlayer: Int) {
        check(countPlayer in 2..23) { "Игроков должно быть от 2 до 23" }
        val p = RandomUtils.getPrimeNumber()
        // Создаем список игроков
        val playerList = mutableListOf<Player>()
        for (i in 0..countPlayer) {
            playerList.add(Player(p))
        }
        // первый игрок генерирует колоду
        val deskMap = playerList[0].generateDeck(deckNames)
        // каждый игрок шифрует колоду своим ключём, перемешвает и передает её другому
        val encryptDecks = playerList.fold(deskMap.keys.toList()) { acc, player ->
            player.encryptDeck(acc)
        }
        // расчитываем количество карт на одного игрока
        val countCartToPlayer: Int = 2
        // все игроки берут свои карты
        val remainingDeck = playerList.fold(encryptDecks) { acc, player ->
            player.getEncryptCart(acc, countCartToPlayer)
        }
        // Расшифровываем карты
        playerList.forEach { currentPlayer ->
            // Получаем зашифрованную колоду
            val encryptDeck = currentPlayer.getDeckInHandsEncrypt()
            // Определяем других игроков, кроме текущего
            val otherPlayers = playerList.filter { it != currentPlayer }
            // Другие игроки расшифровывают колоду
            val decryptDeck = otherPlayers.fold(encryptDeck) { acc, player ->
                player.decryptDeck(acc)
            }
            // Текущий игрок делает последнее расшифрование и получает первоначальную колоду
            currentPlayer.setLastDeck(decryptDeck)
        }
        // Все игроки получили свои карты, пусть они покажут свои карты
        playerList.forEachIndexed { index, player ->
            println("Игрок №${index+1}:")
            player.showDeckInHand(deskMap)
            println("")
        }
    }
}
