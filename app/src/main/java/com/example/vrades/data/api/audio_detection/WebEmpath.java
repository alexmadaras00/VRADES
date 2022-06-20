package com.example.vrades.data.api.audio_detection;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WebEmpath {
    public static final String API_ENDPOINT = "https://api.webempath.net/v2/analyzeWav";

    public void detect(String value) throws Exception {

        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.STRICT)
                .setContentType(ContentType.MULTIPART_FORM_DATA)
                .addTextBody("apikey", "HX4jarcndIhdyTqUAR7XqxtFsXxsnVOalyr2DVTlh4U")
                .addBinaryBody("wav", new java.io.File(value));

        HttpPost httpPost = new HttpPost(API_ENDPOINT);
        httpPost.setEntity(builder.build());
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse resp = client.execute(httpPost)) {

            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println(EntityUtils.toString(resp.getEntity()));
            }
        }
    }
}
