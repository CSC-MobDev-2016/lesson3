package com.csc.lesson3.apis.flickr_image_search;

import com.csc.lesson3.apis.common.ApiMethodService;
import com.csc.lesson3.apis.common.ApiRequestResult;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pavel on 3/7/2016.
 */
public class FlickrImageSearchApiService extends ApiMethodService<List<String>> {

    private static final String FLICKR_API_KEY = "100b74bf4dc4778c1630f77465fba029";

    private static final int NUM_IMAGES = 15;

    private final OkHttpClient client = new OkHttpClient();

    public void fetchImages(final String query) {
        launchRequest(new Callable<ApiRequestResult<List<String>>>() {
            @Override
            public ApiRequestResult<List<String>> call() throws Exception {
                return parseResponse(client.newCall(buildRequest(query)).execute());
            }
        });
    }

    private Request buildRequest(String query) throws UnsupportedEncodingException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.flickr.com")
                .addPathSegment("services")
                .addPathSegment("rest")
                .addQueryParameter("method", "flickr.photos.search")
                .addQueryParameter("api_key", FLICKR_API_KEY)
                .addQueryParameter("text", URLEncoder.encode(query, "UTF-8"))
                .addQueryParameter("per_page", String.valueOf(NUM_IMAGES))
                .build();
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    private ApiRequestResult<List<String>> parseResponse(Response response)
            throws XmlPullParserException, IOException {

        if (response.code() != 200) {
            return new ApiRequestResult.Error<>(new Exception("Code " + response.code()));
        }
        return new ApiRequestResult.Success<>(new FlickrResponseParser().parse(response.body().string()));
    }
}
