import hamilton.GraphManager

fun main() {
    val manager = GraphManager()
    //val path = "src/hamilton/graphtest.txt"
    val countNodes = 100
    //var graph = manager.readGraph(path)
    val graph = manager.generateGraph(countNodes)
    manager.saveGraph(graph = graph, path = "src/hamilton/graph1.txt")
    graph.permutationGraph()
}