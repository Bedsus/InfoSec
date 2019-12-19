package hamilton

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.random.Random

class GraphManager {

    fun readGraph(path: String): HamiltonGraph {
        println("Чтение графа $path")
        val reader = BufferedReader(FileReader(path))
        val edgeList = mutableListOf<Pair<Int, Int>>()
        val hamiltonPath = mutableListOf<Int>()
        var lineNumber = 1
        try {
            var list: List<Int>? = reader.readEdge()
            while (list != null && list.isNotEmpty()) {
                when {
                   list.size == 2 -> edgeList.add(Pair(list[0], list[1]))
                   edgeList.isNotEmpty() -> hamiltonPath.addAll(list)
                   else -> throw IllegalStateException(
                           "Ошибка чтения графа! ($lineNumber)"
                   )
                }
                lineNumber++
                list = reader.readEdge()
            }
        } catch (ex: Exception) {
            throw IllegalStateException(
                "Ошибка чтения графа! ($lineNumber) ${ex.localizedMessage}"
            )
        }
        return HamiltonGraph(edgeList, hamiltonPath).apply { show() }
    }

    fun generateGraph(countNodes: Int): HamiltonGraph {
        print("Генерация графа $countNodes .. ")
        var nodes: List<Int> = (1..countNodes).shuffled()
        val hamiltonPath = nodes
        var edgeList = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until countNodes - 1) {
            edgeList.add(Pair(nodes[i], nodes[i+1]))
        }
        val additionalNodesCount = Random.nextInt(1, countNodes- 1)
        nodes = nodes.shuffled()
        for (i in 0 until additionalNodesCount) {
            edgeList.add(Pair(nodes[i], nodes[i+1]))
        }
        edgeList = edgeList.shuffled()
                .toMutableList()
        print("готово\n")
        return HamiltonGraph(edgeList, hamiltonPath).apply { show() }
    }

    fun saveGraph(graph: HamiltonGraph, path: String) {
        print("Создание файла $path .. ")
        val buffer = File(path).bufferedWriter()
        graph.apply {
            edgeList.forEach { buffer.write("${it.first} ${it.second}\n") }
            hamiltonPath.forEach { buffer.write("$it ") }
        }
        buffer.close()
        print("готово\n")
    }

    private fun BufferedReader.readEdge(): List<Int>? {
        return readLine()
                ?.split(" ")
                ?.map { it.toInt() }
    }
}

class HamiltonGraph(
    var edgeList: MutableList<Pair<Int, Int>>,
    val hamiltonPath: List<Int>
) {
    fun show() {
        println("Вершин в графе G: ${hamiltonPath.size}")
        println("Ребер в графе G: ${edgeList.size}")
        println("Ребра графа G: $edgeList")
        println("Гамильтонов путь G: $hamiltonPath")
    }

    fun permutationGraph() {
        edgeList = edgeList.shuffled()
                .toMutableList()
        println("Вершин в графе H: ${hamiltonPath.size}")
        println("Ребер в графе H: ${edgeList.size}")
        println("Ребра графа H: $edgeList")
        println("Гамильтонов путь H: $hamiltonPath")
    }
}