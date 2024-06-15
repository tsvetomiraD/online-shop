package uni.project.online.shop.service;

import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.NameValuePair;
import uni.project.online.shop.model.paypal.Token;

import java.io.IOException;
import java.util.*;

@Service
public class RequestService {
    @Autowired
    Gson gson;

    public <T> T request(Object bodyC, String url, String type, Class<T> responseClass, Map<String, String> headers) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String json = gson.toJson(bodyC);
        RequestBody body = json != null ? RequestBody.create(json, JSON) : null;
        Request.Builder requestBuilder = new Request.Builder().url(url);

        Headers.Builder headersBuilder = new Headers.Builder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }
        Headers headers1 = headersBuilder.build();
        switch (type.toUpperCase()) {
            case "GET" -> requestBuilder.get();
            case "POST" -> {
                assert body != null;
                requestBuilder.post(body);
            }
            case "PUT" -> {
                assert body != null;
                requestBuilder.put(body);
            }
            case "DELETE" -> requestBuilder.delete(body);
            case "PATCH" -> {
                assert body != null;
                requestBuilder.patch(body);
            }
            default -> throw new IllegalArgumentException("Unsupported method type: ");
        }
        requestBuilder.headers(headers1);
        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            if (response.body() != null && responseClass != null) {
                return gson.fromJson(response.body().charStream(), responseClass);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return null;
    }


    public Token getTokenRequest(String url, Map<String, String> headers, List<NameValuePair> formData) {
        OkHttpClient client = new OkHttpClient();
        Headers.Builder headersBuilder = new Headers.Builder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }
        Headers okhttpHeaders = headersBuilder.build();

        // Build Form Body
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (NameValuePair pair : formData) {
            formBodyBuilder.add(pair.getName(), pair.getValue());
        }
        RequestBody formBody = formBodyBuilder.build();

        // Build Request
        Request request = new Request.Builder()
                .url(url)
                .headers(okhttpHeaders)
                .post(formBody)
                .build();

        // Execute Request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response);
            }
            if (response.body() != null) {
                return gson.fromJson(response.body().charStream(), Token.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
