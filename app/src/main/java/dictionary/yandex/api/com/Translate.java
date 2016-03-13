package dictionary.yandex.api.com;

import get.protocol.com.GETRequest;

import java.util.Hashtable;


public class Translate extends YandexTranslatorAPI {

    private GETRequest request = new GETRequest(SERVICE_URL, ENCODING);

    public WordDescription execute(final String text, final Language from ,final Language to) throws Exception {
        Hashtable<String, String> parameters = new Hashtable<>();
        parameters.put("key", apiKey);
        parameters.put("text", text);
        parameters.put("lang", from + "-" + to);
        return parseResponse(request.sendGet(parameters));
    }
}
