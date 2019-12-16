import hamilton.GraphReader

fun main() {
    val graph = GraphReader().readGraph("src/hamilton/graphtest.txt")
    println("Ребра графа: ${graph.edgeList}")
    println("Гамильтонов путь: ${graph.hamiltonPath}")
}