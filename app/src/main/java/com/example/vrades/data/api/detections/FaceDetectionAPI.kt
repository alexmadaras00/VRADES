package com.example.vrades.data.api.detections

import kong.unirest.HttpResponse
import kong.unirest.Unirest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FaceDetectionAPI {

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun detectFaces(link: String?): String? = withContext(Dispatchers.IO) {
        val response: HttpResponse<String>? =
            Unirest.post("https://emotion-detection2.p.rapidapi.com/emotion-detection")
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "017712ec97mshef91d58f7b7543cp1c946ajsna0d0ddd111da")
                .header("X-RapidAPI-Host", "emotion-detection2.p.rapidapi.com")
                .body("{\r\n    \"url\": \"$link\"\r\n}")
                .asString()

        println(response)

        return@withContext response?.body
    }

}
