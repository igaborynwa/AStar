package com.example.astar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="nodes")
data class Node (
    @PrimaryKey(autoGenerate = false) var nodeId: Long?,
    @ColumnInfo(name="number") var number: Int,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "longitude") var longitude: Double,
    @ColumnInfo(name = "name") var name: String?
    )
