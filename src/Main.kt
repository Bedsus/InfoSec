import hamilton.GraphManager

fun main() {
    val manager = GraphManager()
    val path = "src/hamilton/graphtest.txt"
    val countNodes = 100000
    println("Чтение графа $path")
    var graph = manager.readGraph(path)
    println("Генерация графа $countNodes")
    graph = manager.generateGraph(countNodes)
    manager.saveGraph( graph = graph, path = "src/hamilton/graph.txt")
}