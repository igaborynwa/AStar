package com.example.astar.algorithm

import android.location.Location
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object AStar {
    lateinit var path :ArrayList<ANode>
    private lateinit var graph: Graph

    fun initGraph(g: Graph){
        graph=g
    }
    /**
     * Kiszámítja a két pont közötti légvonalbeli távolságot, ami a heurisztika
     */
    fun calculateHeuristics(from: ANode, target: ANode){
        val results= FloatArray(3)
        Location.distanceBetween(from.node.latitude,from.node.longitude,target.node.latitude,target.node.longitude, results)
        from.H=results[0].toInt()
    }

    /**
     * A closedList-ből elmenti a kalkulált útvonalat
     */
    private fun savePath(target: ANode){
        path= ArrayList()
        path.add(target)
        var next = target.parent
        while (next!=null){
            path.add(next)
            next = next.parent
        }
        path.reverse()
    }

    /**
     * A gráf minden értékét kinullázza, hogy egy tiszta gráfban kezdjük el az algoritmus futását
     */
    private fun nullGraph(){
        for(node in graph.nodes) {
            node.G=0
            node.F=0
            node.H=0
            node.parent=null
        }
    }

    /**
     * Megkeresi a legjobb útvonalat a két megadott indexű csúcs között
     */
    fun findPath(s: Int, t: Int): Boolean{
        nullGraph()
        //Azon pontok, melyeknek zajlik a vizsgálata
        val openList =ArrayList<ANode>()
        //Azon pontok, melyeknek befejeztük a vizsgálatát
        val closedList = ArrayList<ANode>()
        //Lekérjük a két csúcsot a számuk alapján, listák ürítése
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
            var neighbours = getAllNeighbours(bestNode, graph)
            if(neighbours.isNotEmpty()){
                for(node in neighbours.keys){
                    if(!closedList.contains(node)){
                        var g = bestNode.G+ neighbours.getValue(node)

                        if(!openList.contains(node)){
                            calculateHeuristics(node, target)
                            node.G=g
                            node.F=node.G+node.H
                            node.parent=bestNode
                            openList.add(node)
                        }
                        else{
                            if(node.G> g){
                                node.G = g
                                node.parent=bestNode
                                node.F=node.G+node.H
                                break
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    /**
     * Megadja egy adott pont minden szomszédját, az odajutás költségével együtt
     */
    private fun getAllNeighbours(node: ANode, graph: Graph): HashMap<ANode, Int> {
        var neighbours = HashMap<ANode, Int>()
        for(edge in graph.edges){
            if(edge.nodeIsOnTheEdge(node)){
                neighbours[edge.getNeighbour(node)!!] = edge.weight
            }
        }
        return neighbours
    }


}