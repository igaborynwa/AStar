package com.example.astar.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NodeDAO {
    @Query("SELECT * FROM nodes")
    fun getAllNode(): List<Node>

   @Query("SELECT * FROM nodes WHERE number = :num")
    fun getNodeByNumber(num: Int): Node

    @Insert
    fun insertNode(vararg nodes: Node)

    @Delete
    fun deleteNode(node: Node)
}