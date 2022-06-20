package com.example.vrades.data.api.audio_detection

import org.apache.http.HttpStatus
import org.apache.http.client.HttpResponseException
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.io.File

class WebEmpath {
    @Throws(Exception::class)
    fun detect(value: String?) {
        val builder = MultipartEntityBuilder.create()
            .setMode(HttpMultipartMode.STRICT)
            .setContentType(ContentType.MULTIPART_FORM_DATA)
            .addTextBody("apikey", "HX4jarcndIhdyTqUAR7XqxtFsXxsnVOalyr2DVTlh4U")
            .addBinaryBody("wav", File(value.toString()))
        println("here")
        val httpPost = HttpPost(API_ENDPOINT)
        httpPost.entity = builder.build()
        try {
            HttpClients.createDefault().use { client ->
                client.execute(httpPost).use { resp ->
                    if (resp.statusLine.statusCode == HttpStatus.SC_OK) {
                        println(EntityUtils.toString(resp.entity))
                    }
                }
            }
        } catch (e: HttpResponseException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val API_ENDPOINT = "https://api.webempath.net/v2/analyzeWav"
    }
}