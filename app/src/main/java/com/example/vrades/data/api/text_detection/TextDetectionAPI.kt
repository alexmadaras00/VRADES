package com.example.vrades.api.text_detection

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import okhttp3.*
import java.io.IOException


//
//class TextDetectionAPI @Inject constructor(private val detectTextRepositoryRepository: DetectTextRepository) {
//    suspend operator fun invoke(text: String?) = detectTextRepositoryRepository.detectText(text)
//}
class TextDetectionAPI {
    fun detectText(text: String): String {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val client = OkHttpClient().newBuilder().build();
        val mediaType = MediaType.parse("text/plain");
        val body = RequestBody.create(mediaType, text);
        println("Initial text: $text")
        try {
            val request = Request.Builder()
                .url("https://api.apilayer.com/text_to_emotion")
                .addHeader("apikey", "GUqc6Wq6hDj7u5bLCCjU9JHT6Uc6oflt")
                .method("POST", body).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        // Get response
                        println(response.body().toString())

                    } else {
                        println(response.message())
                    }
                }

            })

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "None"
    }

}