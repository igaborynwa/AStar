package com.example.astar.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EdgeDAO {

    @Query("SELECT * FROM edges")
    fun getAllEdge(): List<Edge>

    @Insert
    fun insertEdge(vararg edges: Edge)

    @Delete
    fun deleteEdge(edge: Edge)
}