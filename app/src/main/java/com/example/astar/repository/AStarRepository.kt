package com.example.astar.repository

import com.example.astar.algorithm.Graph
import com.example.astar.algorithm.AStar

class AStarRepository(val graph: Graph) {

    fun calculate() : String{
        val astar = AStar(graph)
        var pathFound = astar.findPath(589, 728)
        if(pathFound){
            var p="Útvonal: "
            for( n in astar.path){
                p+= n.node.number.toString() + " - "
            }
            p= p.dropLast(3)
            return p
        }
        else{
            return "Nincs útvonal"
        }

    }
}