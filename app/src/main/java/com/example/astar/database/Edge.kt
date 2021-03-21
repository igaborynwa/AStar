package com.example.astar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="edges")
data class Edge (
    @PrimaryKey(autoGenerate = true) var edgeId: Long?,
    @ColumnInfo(name = "node1") var node1: Int,
    @ColumnInfo(name = "node2") var node2: Int,
    @ColumnInfo(name = "length") var length: Int
        )