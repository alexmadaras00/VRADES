package com.example.vrades.domain.repositories

import com.example.vrades.domain.model.LifeHack
import com.example.vrades.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface VradesRepository {
    suspend fun getEmotions(): Flow<Response<Map<String, String>>>
    suspend fun getLifeHacks(): Flow<Response<List<LifeHack>>>
    suspend fun getDataAudioTest(): Flow<Response<List<String>>>
    suspend fun getDataWritingTest(): Flow<Response<List<String>>>
    suspend fun getPictureByName(name: String): Flow<Response<String>>
    suspend fun getPictures(): Flow<Response<Map<String, String>>>
}