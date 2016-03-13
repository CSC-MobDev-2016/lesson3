package picture.bing.api.com;

import android.util.Base64;

import get.protocol.com.GETRequest;

import java.util.Hashtable;

public class BingPicture extends BingPictureAPI {

    private GETRequest request = new GETRequest(SERVICE_URL, ENCODING);

    public PicturesDescription execute(final String text, int count, int offset) throws Exception {
        Hashtable<String, String> parameters = new Hashtable<>();
        parameters.put("Query", "'" + text + "'");
        parameters.put("$format", "json");
        parameters.put("$top", Integer.toString(count));
        parameters.put("$skip", Integer.toString(offset));
        Hashtable<String, String> requestProperty = new Hashtable<>();
        requestProperty.put("Authorization", "Basic " + Base64.encodeToString((":" + apiKey).getBytes(), Base64.DEFAULT));
        return parseResponse(request.sendGet(parameters, requestProperty));
    }

    public PicturesDescription execute(final String text, int count) throws Exception {
        return execute(text, count, 0);
    }
}
