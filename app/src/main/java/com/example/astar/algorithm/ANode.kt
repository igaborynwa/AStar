package com.example.astar.algorithm

import com.example.astar.database.Node

class ANode(var node: Node) {
    /**Az eddigi idejutás költsége**/
    var G = 0
    /**Becslés, a célig tartó út hosszára**/
    var H = 0
    /**Becsült költség a kezdőpontból a célig való eljutásra a ponton keresztül**/
    var F = 0
    /**A pont, ahonnan ide jutottunk**/
    var parent: ANode? = null
}