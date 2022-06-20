package com.example.vrades.api.text_detection

import com.example.vrades.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import ru.gildor.coroutines.okhttp.await
import javax.inject.Singleton

@Singleton
class DetectTextRepositoryImpl : DetectTextRepository {

    override suspend fun detectText(text: String?): Flow<Response<JSONArray>> = flow {
        try {
            emit(Response.Loading)
            val client = OkHttpClient().newBuilder().build()
            val mediaType = MediaType.parse("text/plain")
            val body = RequestBody.create(mediaType, text.toString())
//            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//            StrictMode.setThreadPolicy(policy)
            val request = Request.Builder()
                .url("https://api.apilayer.com/text_to_emotion")
                .addHeader("apikey", "tqBVQpxtFwQPTJ7pUpz10egoTkD8hwV8")
                .method("POST", body)
                .build()
            val response = client.newCall(request).await()
            println(response)


        } catch (e: Exception) {
            emit(Response.Error((e.message ?: e.message).toString()))

        }
    }
}