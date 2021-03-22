package com.example.astar.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.astar.algorithm.Graph
import com.example.astar.database.*
import com.example.astar.repository.AStarRepository
import com.example.astar.repository.DBRepository
import com.example.astar.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private var dbRepository: DBRepository
    private var fileRepository: FileRepository
    private lateinit var aStarRepository: AStarRepository
    var graph = Graph()
    var path: MutableLiveData<String>

    init {
        val nodeDao = AppDatabase.getInstance(application).nodeDao()
        val edgeDao = AppDatabase.getInstance(application).edgeDao()
        dbRepository=DBRepository(nodeDao, edgeDao)
        fileRepository = FileRepository()
        path= MutableLiveData()


    }
    fun readFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            val nodes = dbRepository.getAllNode()
            val edges = dbRepository.getAllEdge()
            graph.createGraph(nodes, edges)
        }
    }

    fun readFromCSV(nodeCSV: InputStream, edgeCSV: InputStream) {
        val nodeList = fileRepository.readNodes(nodeCSV)
        val edgeList= fileRepository.readEdges(edgeCSV)
        viewModelScope.launch(Dispatchers.IO){
            dbRepository.insertNodes(nodeList)
            dbRepository.insertEdges(edgeList)
        }
    }

    fun calculatePath(){
        aStarRepository= AStarRepository(graph)
        path.value=aStarRepository.calculate()
    }




}