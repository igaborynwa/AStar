package com.example.astar.repository

import com.example.astar.algorithm.AStar

class AStarRepository {

    fun calculate(from: Int, to: Int) : String{
        var pathFound = AStar.findPath(from, to)
        if(pathFound){
            var p="Útvonal: "
            for( n in AStar.path){
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