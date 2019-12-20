import hamilton.GraphManager

fun main() {
    val manager = GraphManager()
    val path = "src/hamilton/graphtest.txt"
    val countNodes = 90
    //val graph = manager.readGraph(path)
    val graph1 = manager.generateGraph(countNodes)
    //manager.saveGraph(graph = graph1, path = "src/hamilton/graph1.txt")
    graph1.permutationGraphH()
}