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
        return HamiltonGraph(edgeList, hamiltonPath).apply { showH() }
    }

    fun generateGraph(countNodes: Int): HamiltonGraph {
        print("${violetColor}Генерация графа $countNodes .. $whiteColor")
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
        print("${violetColor}готово$whiteColor\n")
        return HamiltonGraph(edgeList, hamiltonPath).apply { showH() }
    }

    fun saveGraph(graph: HamiltonGraph, path: String) {
        print("${violetColor}Создание файла $path .. $whiteColor")
        val buffer = File(path).bufferedWriter()
        graph.apply {
            edgeList.forEach { buffer.write("${it.first} ${it.second}\n") }
            hamiltonPath.forEach { buffer.write("$it ") }
        }
        buffer.close()
        print("${violetColor}готово$whiteColor\n")
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
    fun show1() {
        println("\nКаков гамильтонов цикл для графа H?\n")
        println("Вершин в графе G: ${hamiltonPath.size}")
        println("Ребер в графе G: ${edgeList.size}")
        println("Ребра графа G: $edgeList")
        println("Гамильтонов путь G: $hamiltonPath")
    }

    fun permutationGraph1() {
        println("\nДействительно ли граф H изоморфен G?\n")
        edgeList = edgeList.shuffled()
                .toMutableList()
        println("Вершин в графе H: ${hamiltonPath.size}")
        println("Ребер в графе H: ${edgeList.size}")
        println("Ребра графа H: $edgeList")
        println("Гамильтонов путь H: $hamiltonPath")
    }

    fun showH() {
        println("\n${violetColor}Каков гамильтонов цикл для графа H?$whiteColor\n")
        show("G")
    }


    fun permutationGraphH() {
        println("\n${violetColor}Действительно ли граф H изоморфен G?$whiteColor\n")
        edgeList = edgeList.shuffled()
                .toMutableList()
        show("H")
    }

    private fun show(graph: String) {
        println("${yellowColor}Вершин в графе $graph:$whiteColor ${hamiltonPath.size} " +
                "Ребер в графе $graph: ${edgeList.size}")
        println("${yellowColor}Ребра графа $graph:$whiteColor $edgeList")
        println("${yellowColor}Гамильтонов путь $graph:$whiteColor ${
        hamiltonPath.map{ it.toString() }.reduce { i, j -> "$i -> $j" }}"
        )
    }
}

val whiteColor = 27.toChar() + "[30m"
val violetColor = 27.toChar() + "[35m"
val yellowColor = 27.toChar() + "[32m"
