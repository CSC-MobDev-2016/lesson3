package com.csc.lesson3.apis.flickr_image_search;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 3/12/2016.
 */
public class FlickrResponseParser {
    private static final String ULR_FORMAT =
            "https://farm%s.staticflickr.com/%s/%s_%s.jpg";

    private XmlPullParser xmlParser;
    private int currentEventType;

    public List<String> parse(String response) throws XmlPullParserException, IOException {
        List<String> urls = new ArrayList<>();

        xmlParser =  XmlPullParserFactory.newInstance().newPullParser();
        xmlParser.setInput(new StringReader(response));

        makeStep();
        while (currentEventType != XmlPullParser.END_DOCUMENT) {
            if (atStartTagWithName("photo")) {
                urls.add(parseNextUrl());
            }
            makeStep();
        }
        return urls;
    }

    private void makeStep() throws XmlPullParserException, IOException {
        currentEventType = xmlParser.next();
    }

    private String parseNextUrl()
            throws XmlPullParserException, IOException {
        return makeUrl(getAttr("id"), getAttr("secret"),
                getAttr("server"), getAttr("farm"));
    }

    private String makeUrl(String id, String secret,
                           String server, String farm) {
        return String.format(ULR_FORMAT, farm, server, id, secret);
    }

    private String getAttr(String name) {
        return xmlParser.getAttributeValue(null, name);
    }

    private boolean atStartTagWithName(String name) {
        return currentEventType == XmlPullParser.START_TAG
                && xmlParser.getName().equals(name);
    }

    private boolean atEndTagWithName(String name) {
        return currentEventType == XmlPullParser.END_TAG
                && xmlParser.getName().equals(name);
    }
}
