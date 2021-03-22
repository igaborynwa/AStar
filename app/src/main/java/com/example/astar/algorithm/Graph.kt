package com.example.astar.algorithm

import com.example.astar.database.Edge
import com.example.astar.database.Node

class Graph {
    var nodes = ArrayList<ANode>()
    var edges = ArrayList<AEdge>()


    fun createGraph(ns: ArrayList<Node>, es: ArrayList<Edge>){
        edges=ArrayList()
        nodes=ArrayList()
        for(node in ns){
            nodes.add(ANode(node))
        }
        for(e in es){
            edges.add(AEdge(arrayListOf(getNodeByNumber(e.node1), getNodeByNumber(e.node2)), e.length))
        }
    }

    fun getNodeByNumber(number: Int): ANode?{
        for(node in nodes){
            if(node.node.number==number) return node
        }
        return null
    }

}