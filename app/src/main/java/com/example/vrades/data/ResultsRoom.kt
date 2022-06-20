package com.example.vrades.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results_table")
class ResultsRoom(
    @PrimaryKey @ColumnInfo(name = "face_detection") val results1: Map<String, Float>,
    @PrimaryKey @ColumnInfo(name = "audio_detection") val results2: Map<String, Float>,
    @PrimaryKey @ColumnInfo(name = "writing_detection") val results3: Map<String, Float>
)

