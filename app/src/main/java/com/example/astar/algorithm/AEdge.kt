package com.example.astar.algorithm

import com.example.astar.algorithm.ANode

class AEdge(n: ArrayList<ANode?>, w: Int) {

    var nodes: ArrayList<ANode?> = n
    var weight = w

    fun nodeIsOnTheEdge(n: ANode): Boolean{
        return nodes.contains(n)
    }

    fun getNeighbour(n: ANode): ANode? {
        return if (nodes[0]!!.node.number == n.node.number )
            nodes[1]
        else nodes[0]
    }


}