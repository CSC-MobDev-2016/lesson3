package com.csc.lesson3;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public final class Translate extends YandexTranslatorAPI {

    private static final String SERVICE_URL = "https://dictionary.yandex.net/api/v1/dicservice/lookup?";

    private Translate(){}

    public static ArrayList execute(final String text, final Language from, final Language to) throws Exception {
        validateServiceState(text);
        final String params =
                PARAM_API_KEY + URLEncoder.encode(apiKey,ENCODING)
                        + PARAM_LANG_PAIR + URLEncoder.encode(from.toString(),ENCODING) + URLEncoder.encode("-",ENCODING) + URLEncoder.encode(to.toString(),ENCODING)
                        + PARAM_TEXT + URLEncoder.encode(text, ENCODING);
        final URL url = new URL(SERVICE_URL + params);
        return retrieveResponse(url);
    }

    private static void validateServiceState(final String text) throws Exception {
        final int byteLength = text.getBytes(ENCODING).length;
        if(byteLength>10240) { // TODO What is the maximum text length allowable for Yandex?
            throw new RuntimeException("TEXT_TOO_LARGE");
        }
        validateServiceState();
    }

}
