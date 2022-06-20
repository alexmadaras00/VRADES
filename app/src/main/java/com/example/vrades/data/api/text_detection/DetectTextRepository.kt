package com.example.vrades.api.text_detection

import com.example.vrades.model.Response
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import javax.inject.Singleton

@Singleton
interface DetectTextRepository {
    suspend fun detectText(text: String?): Flow<Response<JSONArray>>
}