package com.example.vrades.data.api.detections

import kong.unirest.HttpResponse
import kong.unirest.Unirest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TextDetectionAPI {

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun detectText(text: String): String = withContext(Dispatchers.IO) {
        val response: HttpResponse<String> =
            Unirest.post("https://emodex-emotions-analysis.p.rapidapi.com/rapidapi/emotions")
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "f2cc4250b7mshbd6de2d06d653eap1103cfjsnf77f5c2e6c97")
                .header("X-RapidAPI-Host", "emodex-emotions-analysis.p.rapidapi.com")
                .body("{\r\n    \"sentence\": \"$text\"\r\n}")
                .asString()
        return@withContext response.body
    }

}