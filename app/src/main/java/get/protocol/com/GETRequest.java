package get.protocol.com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;

public class GETRequest {
    private String url;
    private String encoding;
    private int TIME_OUT = 5000;

    public GETRequest(final String url, final String encoding) {
        this.url = url;
        this.encoding = encoding;
    }

    public GETRequest(final String url, final String encoding, int time_out) {
        this(url, encoding);
        TIME_OUT = time_out;
    }


    public String sendGet(final Hashtable<String, String> parameters, final Hashtable<String, String> requestProperty) throws Exception {
        boolean firstStep = true;
        String url = this.url;
        if (parameters != null) {
            Enumeration<String> keys = parameters.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = URLEncoder.encode(parameters.get(key), encoding);
                key = URLEncoder.encode(key, encoding);
                url += (firstStep ? '?' : '&') + key + '=' + value;
                firstStep = false;
            }
        }

        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);
        connection.setRequestProperty("Content-Type","text/plain; charset=" + encoding);
        connection.setRequestProperty("Accept-Charset", encoding);

        if (requestProperty != null) {
            Enumeration<String> requestKeys = requestProperty.keys();
            while (requestKeys.hasMoreElements()) {
                String key = requestKeys.nextElement();
                String value = requestProperty.get(key);
                connection.setRequestProperty(key, value);
            }
        }

        connection.setRequestMethod("GET");
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }
        input.close();

        return response.toString();
    }

    public String sendGet(final Hashtable<String, String> parameters) throws Exception {
        return sendGet(parameters, null);
    }
}
