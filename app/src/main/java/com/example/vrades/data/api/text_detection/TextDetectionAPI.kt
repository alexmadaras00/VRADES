package com.example.vrades.api.text_detection

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
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
            val response = client.newCall(request).execute()
            if ((response.code()) == 200) {
                // Get response
                println(response.message())
                val jsonData = response.message()

                // Transform reponse to JSon Object
                val json = JSONObject(jsonData);

                // Use the JSon Object
                println("code: ${response.code()}, and ${response.request()}, format: $jsonData");

                return json.keys().next()

            } else {
                println(response.body())
            }
        } catch (e: IOException) {
            println(e.toString());
        } catch (e: JSONException) {
            println(e)
        }


        return "Nothing"
    }

}