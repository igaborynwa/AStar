package com.example.astar.repository

import com.example.astar.database.Edge
import com.example.astar.database.Node
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class FileRepository {
    fun readNodes(nodeCSV: InputStream): ArrayList<Node>{
       val nodeList = ArrayList<Node>()
        try {
            val reader = BufferedReader(InputStreamReader(nodeCSV))
            var line =reader.readLine()
            while(line!=null){
                val nodeArray = line.split(";")
                val node = Node(nodeArray[0].toLong(), nodeArray[1].toInt(), nodeArray[2].toDouble(), nodeArray[3].toDouble(), nodeArray[4])
                line =reader.readLine()
                nodeList.add(node)
            }
            reader.close()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
       return nodeList
    }

    fun readEdges(edgeCSV: InputStream): ArrayList<Edge> {
        val edgeList= ArrayList<Edge>()
        try {
            val reader = BufferedReader(InputStreamReader(edgeCSV))
            var line =reader.readLine()
            while(line!=null){
                val edgeArray = line.split(";")
                val edge = Edge(edgeArray[0].toLong(), edgeArray[1].toInt(), edgeArray[2].toInt(), edgeArray[3].toInt())
                line =reader.readLine()
                edgeList.add(edge)
            }
            reader.close()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return edgeList
    }
}