package com.csc.lesson3;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by JV on 12.03.2016.
 */
public class ImageLoadTranslate extends BingSearchImageAPI {

    private static final String bingUrlPattern = "https://api.datamarket.azure.com/Bing/Search/Image?Query=%%27%s%%27&$format=JSON";


    private ImageLoadTranslate(){}

    public static ArrayList<String> execute(final String text) {

        String query;
        try {
            query = URLEncoder.encode(text, Charset.defaultCharset().name());

            String bingUrl = String.format(bingUrlPattern, query);

            URL url = new URL(bingUrl);

            return retrieveResponse(url);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
