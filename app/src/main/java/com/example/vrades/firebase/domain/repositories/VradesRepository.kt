package com.example.vrades.firebase.repositories.domain

import com.example.vrades.model.LifeHack
import com.example.vrades.model.Response
import com.example.vrades.model.Test
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface VradesRepository  {
   suspend fun getEmotions(): Flow<Response<List<String>>>
   suspend fun getLifeHacks():  Flow<Response<List<LifeHack>>>
   suspend fun getTests():  Flow<Response<List<Test>>>
   suspend fun getDataAudioTest(): Flow<Response<List<String>>>
   suspend fun getDataWritingTest(): Flow<Response<List<String>>>

}