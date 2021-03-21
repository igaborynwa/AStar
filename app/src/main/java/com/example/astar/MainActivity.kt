package com.example.astar

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.astar.algorithm.AStar
import com.example.astar.database.AppDatabase
import com.example.astar.database.Edge
import com.example.astar.database.Node
import com.example.astar.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
     lateinit var graph: Graph
     var nodes = ArrayList<Node>()
    var edges = ArrayList<Edge>()
    lateinit var prefs : SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE)
        //insertData()
       binding.btnLoad.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
               readFromDB()
            }
        }
        binding.btnSearch.setOnClickListener {
            initGraph()
            calculate()
        }
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean("firstrun", true)) {
            readNodesFromCSV()
            readEdgesFromCSV()
            prefs.edit().putBoolean("firstrun", false).apply()
        }
    }


    private fun insertData(){
        /*val dbThread =Thread{
            AppDatabase.getInstance(this@MainActivity).nodeDao().insertNode(Node(1,722,47.59132,20.6234,"Laskó-torkolat"))
            AppDatabase.getInstance(this@MainActivity).nodeDao().insertNode(Node(2,591,47.58946,20.62305,"Kubik1"))
            AppDatabase.getInstance(this@MainActivity).nodeDao().insertNode(Node(3,590,47.58934,20.62457,"Kubik2"))
            AppDatabase.getInstance(this@MainActivity).nodeDao().insertNode(Node(4,723,47.59062,20.62622,"Kubik3"))
            AppDatabase.getInstance(this@MainActivity).nodeDao().insertNode(Node(5,728,47.59282,20.62573,"Kubik4"))
            AppDatabase.getInstance(this@MainActivity).nodeDao().insertNode(Node(6,589,47.58843,20.62548,"Kubik5"))

            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(1,722,591,207))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(2,722,590,237))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(3,722,723,228))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(4,722,728,245))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(5,591,590,182))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(6,591,723,269))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(7,723,728,240))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(8,590,723,180))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(9,590,589,122))
            AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(Edge(10,723,589,247))
        }
        dbThread.start()*/
    }

    private suspend fun readFromDB(){
            nodes = AppDatabase.getInstance(this@MainActivity).nodeDao().getAllNode() as ArrayList<Node>
            edges = AppDatabase.getInstance(this@MainActivity).edgeDao().getAllEdge() as ArrayList<Edge>

    }

    private fun initGraph(){
        graph = Graph()
        graph.createGraph(nodes, edges)

    }

    private fun readNodesFromCSV(){
        try {
            val reader = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.nodes)))
            var line =reader.readLine()
            while(line!=null){
                val nodeArray = line.split(";")
                val node = Node(nodeArray[0].toLong(), nodeArray[1].toInt(), nodeArray[2].toDouble(), nodeArray[3].toDouble(), nodeArray[4])
                line =reader.readLine()
                lifecycleScope.launch(Dispatchers.IO) {AppDatabase.getInstance(this@MainActivity).nodeDao().insertNode(node)}

            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    private fun readEdgesFromCSV() {
        try {
            val reader = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.edges)))
            var line =reader.readLine()
            while(line!=null){
                val edgeArray = line.split(";")
                val edge = Edge(edgeArray[0].toLong(), edgeArray[1].toInt(), edgeArray[2].toInt(), edgeArray[3].toInt())
                line =reader.readLine()
                lifecycleScope.launch(Dispatchers.IO) {AppDatabase.getInstance(this@MainActivity).edgeDao().insertEdge(edge)}

            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }


    fun calculate() {
        val astar = AStar(graph)
        var pathFound = astar.findPath(589, 728)
        if(pathFound){
            var path="Útvonal: "
            for( n in astar.path){
                path+= n.node.number.toString() + " - "
            }
            path= path.dropLast(3)
            binding.tvPath.text = path
        }
        else{
            binding.tvPath.text = "nincs út"
        }

    }
}