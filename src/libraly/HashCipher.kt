package libraly

interface HashCipher<M, T> {
    fun hash(message: M): T
    fun verification(message: T): Boolean
}