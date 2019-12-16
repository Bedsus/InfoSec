package hamilton

import java.io.BufferedReader
import java.io.FileReader

class GraphReader {

    fun readGraph(path: String): HamiltonGraph {
        val reader = BufferedReader(FileReader(path))
        val edgeList = mutableListOf<Pair<Long, Long>>()
        val hamiltonPath = mutableListOf<Long>()
        var lineNumber = 1
        try {
            var list: List<Long>? = reader.readEdge()
            while (list != null && list.isNotEmpty()) {
                when {
                   list.size == 2 -> edgeList.add(Pair(list[0], list[1]))
                   edgeList.isNotEmpty() -> hamiltonPath.addAll(list)
                   else -> throw IllegalStateException("Ошибка чтения графа! ($lineNumber)")
                }
                lineNumber++
                list = reader.readEdge()
            }
        } catch (ex: Exception) {
            throw IllegalStateException("Ошибка чтения графа! ($lineNumber) ${ex.localizedMessage}")
        }
        return HamiltonGraph(edgeList, hamiltonPath)
    }

    private fun BufferedReader.readEdge(): List<Long>? {
        return readLine()
                ?.split(" ")
                ?.map { it.toLong() }
    }
}

class HamiltonGraph(val edgeList: List<Pair<Long, Long>>, val hamiltonPath: List<Long>)