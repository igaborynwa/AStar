package com.example.astar.repository

import com.example.astar.database.Edge
import com.example.astar.database.EdgeDAO
import com.example.astar.database.Node
import com.example.astar.database.NodeDAO

class DBRepository(val nodeDao: NodeDAO, val edgeDao: EdgeDAO) {

    fun getAllNode(): ArrayList<Node>{
        return nodeDao.getAllNode() as ArrayList<Node>
    }

    fun getAllEdge(): ArrayList<Edge>{
        return edgeDao.getAllEdge() as ArrayList<Edge>
    }

    fun insertEdges(edges: ArrayList<Edge>){
        for(e in edges) edgeDao.insertEdge(e)
    }

    fun insertNodes(nodes: ArrayList<Node>){
        for(n in nodes) nodeDao.insertNode(n)
    }
}