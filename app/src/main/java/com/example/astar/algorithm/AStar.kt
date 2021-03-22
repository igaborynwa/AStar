package com.example.astar.algorithm

import android.location.Location
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AStar(var graph: Graph) {
    //Azon pontok, melyeknek zajlik a vizsgálata
    var openList =ArrayList<ANode>()
    //Azon pontok, melyeknek befejeztük a vizsgálatát
    var closedList = ArrayList<ANode>()
    //A megtalált legjobb útvonal
    var path =ArrayList<ANode>()

    /**
     * Visszaadja a két pont közötti légvonalbeli távolságot, ami a heurisztika
     */
    fun calculateHeuristics(from: ANode, target: ANode){
        val results= FloatArray(3)
        Location.distanceBetween(from.node.latitude,from.node.longitude,target.node.latitude,target.node.longitude, results)
        from.H=results[0].toInt()
    }

    /**
     * Megadaja, hogy a paraméterül kapott listában benne van-e a megadott csúcs
     */
    fun listContains(list: ArrayList<ANode>, node: ANode): Boolean{
        for(n in list){
            if(n.node.number==node.node.number) return true
        }
        return false
    }

    /**
     * A closedList-ből elmenti a kalkulált útvonalat
     */
    fun savePath(target: ANode){
        path.add(target)
        var next = target.parent
        while (next!=null){
            path.add(next)
            next = next.parent
        }
        path.reverse()
    }

    /**
     * Megkeresi a legjobb útvonalat a két megadott indexű csúcs között
     */
    fun findPath(s: Int, t: Int): Boolean{
        //Lekérjük a két csúcsot a számuk alapján
        val start =graph.getNodeByNumber(s)
        val target =graph.getNodeByNumber(t)
        //Ha nem létezik a csúcs, hiba
        if(target ==null || start ==null) return false
        //Kezdőcsúcsot betesszük az openListbe, heurisztikát, becslést számítunk, eddigi út 0
        start.G=0
        calculateHeuristics(start, target)
        start.F=start.G+start.H
        openList.add(start)

        //Amíg az openList nem üres, azaz vannak megvizsgálható pontok:
        while(openList.isNotEmpty()){
            //Megkeressük azt a pontot az openListből, amire legkisebb az eddigi és a célig tartó út költsége, ezt átteszzük a closedList-be
            var bestNode = openList[0]
            var best =openList[0].F
            for(n in openList){
                if(n.F<best){
                    best = n.F
                    bestNode=n
                }
            }
            openList.remove(bestNode)
            closedList.add(bestNode)
            //Ha a cél került át a closedList-be akkor kész
           if(target.node.number == bestNode.node.number){
                savePath(target)
                return true
            }
            /*Lekérjük az átkerült node minden szomszédját, majd mindegyikre:
                Ha a szomszéd closed, akkor tovább
                Kiszámoljuk az eddigi út költségét a szomszédig
                Ha nincs az openListben, akkor beletesszük és kiszámoljuk a heurisztikát
                Ha openListben van, akkor  ellenőrizzük, hogy az idejutás költsége jobb-e mint az eddigi, ha igen akkor cseréljük
                Ha történt változás az utolsó két pontban, akkor újraszámítjuk az eddigi+heurisztika becslést
            */
            var neighbours = getAllNeighbours(bestNode)
            if(neighbours.isNotEmpty()){
                for(node in neighbours.keys){
                    if(!listContains(closedList, node)){
                        node.G = bestNode.G+ neighbours.getValue(node)
                        node.parent = bestNode
                        if(!listContains(openList, node)){
                            openList.add(node)
                            calculateHeuristics(node, target)
                        }
                        else{
                            for(n in openList){
                                if(n.node.number == node.node.number) {
                                    if(n.G> node.G){
                                        n.G = node.G
                                        n.parent=node.parent
                                        break
                                    }
                                }
                            }
                        }
                        node.F=node.G+node.H
                    }
                }
            }
        }
        return false
    }

    /**
     * Megadja egy adott pont minden szomszédját, az odajutás költségével együtt
     */
    private fun getAllNeighbours(node: ANode): HashMap<ANode, Int> {
        var neighbours = HashMap<ANode, Int>()
        for(edge in graph.edges){
            if(edge.nodeIsOnTheEdge(node)){
                neighbours[edge.getNeighbour(node)!!] = edge.weight
            }
        }
        return neighbours
    }


}