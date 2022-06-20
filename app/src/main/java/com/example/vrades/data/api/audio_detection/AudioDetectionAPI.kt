package com.example.vrades.api.audio_detection

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class AudioDetectionAPI {
    fun detectEmotion(file: String) {
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        print(file)
        val body = RequestBody.create(mediaType, "{$file}")
        val request = Request.Builder()
            .url("https://audio-emotion-recognition.p.rapidapi.com/voice-checker")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", "f2cc4250b7mshbd6de2d06d653eap1103cfjsnf77f5c2e6c97")
            .addHeader("X-RapidAPI-Host", "audio-emotion-recognition.p.rapidapi.com")
            .build()
        val response = client.newCall(request).request()
        println(response)
    }

}