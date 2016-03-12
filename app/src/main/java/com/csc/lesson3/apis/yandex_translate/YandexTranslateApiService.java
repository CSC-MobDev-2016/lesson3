package com.csc.lesson3.apis.yandex_translate;

import com.csc.lesson3.apis.common.ApiMethodService;
import com.csc.lesson3.apis.common.ApiRequestResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.Callable;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pavel on 3/7/2016.
 */
public class YandexTranslateApiService extends ApiMethodService<String> {
    private static final String TRANSLATE_API_KEY =
            "trnsl.1.1.20160307T131713Z.ea3b5b816e758c90.f377c856709b4c4bd244d85f6109d1446d8eb7b1";

    private final OkHttpClient client = new OkHttpClient();

    public void fetchTranslation(final String input) {
        launchRequest(new Callable<ApiRequestResult<String>>() {
            @Override
            public ApiRequestResult<String> call() throws Exception {
                return parseResponse(client.newCall(buildRequest(input)).execute());
            }
        });
    }

    private Request buildRequest(String input) throws UnsupportedEncodingException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("translate.yandex.net")
                .addPathSegment("/api/v1.5/tr.json/translate")
                .addQueryParameter("key", TRANSLATE_API_KEY)
                .addQueryParameter("lang", "en-ru")
                .addQueryParameter("text", URLEncoder.encode(input, "UTF-8"))
                .build();
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    private ApiRequestResult<String> parseResponse(Response response) throws JSONException, IOException {
        if (response.code() != 200) {
            return new ApiRequestResult.Error<>(new Exception("Code " + response.code()));
        }

        JSONArray translations = new JSONObject(response.body().string())
                .getJSONArray("text");
        if (translations.length() == 0) {
            return new ApiRequestResult.Error<>(new Exception("Translations not found"));
        }
        String translation = URLDecoder.decode(translations.getString(0), "UTF-8");
        return new ApiRequestResult.Success<>(translation);
    }
}
