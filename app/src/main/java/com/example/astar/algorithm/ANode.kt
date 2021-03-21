package com.example.astar.algorithm

import com.example.astar.database.Node

class ANode(var node: Node) {
    var G = 0 //eddigi idejutás költsége
    var H = 0 //heurisztika a célig
    var F = 0 //becsült költség a kezdőponttól ide jutás + célba jutás
    var parent: ANode? = null //a pont, ahonnan ide érkeztünk
}