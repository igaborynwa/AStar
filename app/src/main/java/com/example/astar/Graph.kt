package com.example.astar

import com.example.astar.algorithm.AEdge
import com.example.astar.algorithm.ANode
import com.example.astar.database.Edge
import com.example.astar.database.Node

class Graph {
    var nodes = ArrayList<ANode>()
    var edges = ArrayList<AEdge>()

    init {
       //loadGraph()
    }

    private fun loadGraph(){
        val A = ANode( Node(1,722,47.59132,20.6234,"Laskó-torkolat"))
        //A.H=20
        val B = ANode(Node(2,591,47.58946,20.62305,"Kubik1"))
        //B.H=20
        val C = ANode(Node(3,590,47.58934,20.62457,"Kubik2"))
        //C.H = 5
        val D = ANode(Node(4,723,47.59062,20.62622,"Kubik3"))
        //D.H=15
        val E = ANode(Node(5,728,47.59282,20.62573,"Kubik4"))
        //E.H=0
        val F = ANode(Node(6,589,47.58843,20.62548,"Kubik5"))
        nodes.add(A); nodes.add(B); nodes.add(C); nodes.add(D); nodes.add(E); nodes.add(F)
        val AB = AEdge(arrayListOf(A,B), 207)
        val AC = AEdge(arrayListOf(A,C), 237)
        val AD = AEdge(arrayListOf(A,D), 228)
        val AE = AEdge(arrayListOf(A,E), 245)
        val BC = AEdge(arrayListOf(B,C), 182)
        val BD = AEdge(arrayListOf(B,D), 269)
        val DE = AEdge(arrayListOf(D,E), 240)
        val CD = AEdge(arrayListOf(C,D), 180)
        val CF = AEdge(arrayListOf(C,F), 122)
        val DF = AEdge(arrayListOf(D,F), 247)
        edges.add(AB);edges.add(AC);edges.add(BC);edges.add(BD);edges.add(DE);edges.add(AD);edges.add(AE);edges.add(CD);edges.add(CF);edges.add(DF)
    }

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