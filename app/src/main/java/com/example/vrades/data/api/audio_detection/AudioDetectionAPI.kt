package com.example.vrades.data.api.audio_detection

import kong.unirest.Unirest
import kong.unirest.json.JSONObject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object AudioDetectionAPI {
    @OptIn(DelicateCoroutinesApi::class)
    fun detectEmotion(file: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                Unirest.post("https://amberscript-audio-and-video-transcription-speech-to-text.p.rapidapi.com/jobs")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("X-RapidAPI-Key", "f2cc4250b7mshbd6de2d06d653eap1103cfjsnf77f5c2e6c97")
                    .header(
                        "X-RapidAPI-Host",
                        "amberscript-audio-and-video-transcription-speech-to-text.p.rapidapi.com"
                    )
                    .body("url=$file")
                    .asJson()


            println(response.body)
            response.ifSuccess {
                println(it)
                println(it.body.toString())
            }
        }

    }
}