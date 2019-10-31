package libraly.contract

interface Cipher {

    val name: String

    /**
     * Генерация случайных чисел для шифрования
     */
    fun generate()

    /**
     * Проверяем удовлетворяют
     */
    fun checkRule()
}