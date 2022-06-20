package com.example.vrades.data.api.face_detection

import kong.unirest.HttpResponse
import kong.unirest.Unirest
import kong.unirest.json.JSONObject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object DetectFaceEmotionAPI {
    // This is your API token
    @OptIn(DelicateCoroutinesApi::class)
    fun detectFaces(link: String?) {
        GlobalScope.launch(Dispatchers.IO) {
            val response: HttpResponse<String> =
                Unirest.post("https://emotion-detection2.p.rapidapi.com/emotion-detection")
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", "017712ec97mshef91d58f7b7543cp1c946ajsna0d0ddd111da")
                    .header("X-RapidAPI-Host", "emotion-detection2.p.rapidapi.com")
                    .body("{\r\n    \"url\": \"$link\"\r\n}")
                    .asString()
            print("before: $response")
            println(response.body)
            response.ifSuccess {
                print(it)
                print(it.body.toString())
            }
        }
    }
}