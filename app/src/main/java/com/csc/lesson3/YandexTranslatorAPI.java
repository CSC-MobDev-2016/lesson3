package com.csc.lesson3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public abstract class YandexTranslatorAPI {
    //Encoding type
    protected static final String ENCODING = "UTF-8";

    protected static String apiKey = "dict.1.1.20160307T095351Z.f9db5a8483e20968.b985dc422debbf008588afb99d7ca80b67490d0b";

    protected static final String PARAM_API_KEY = "key=",
            PARAM_LANG_PAIR = "&lang=",
            PARAM_TEXT = "&text=";



    protected static ArrayList retrieveResponse(final URL url) throws Exception {
        final HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();

        try {
            final int responseCode = uc.getResponseCode();
            final ArrayList result = parseXML(uc.getInputStream());
            if(responseCode!=200) {
                throw new Exception("Error from Yandex API: " + result);
            }
            return result;
        } finally {
            if(uc!=null) {
                uc.disconnect();
            }
        }
    }

   private static ArrayList parseXML(final InputStream inputStream){
       ArrayList<String> listTranslate = new ArrayList<>();

       DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
       f.setValidating(false);

       DocumentBuilder builder = null;
       try {
           builder = f.newDocumentBuilder();
       } catch (ParserConfigurationException e) {
           e.printStackTrace();
       }

       Document doc = null;
       try {
           doc = builder.parse(inputStream);
       } catch (SAXException | IOException e) {
           e.printStackTrace();
       }

       NodeList nList = doc.getElementsByTagName("tr");

       for (int temp = 0; temp < nList.getLength(); temp++) {
           Node nNode = nList.item(temp);
           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;

               listTranslate.add(eElement
                       .getElementsByTagName("text")
                       .item(0)
                       .getTextContent());

               NodeList synList = eElement.getElementsByTagName("syn");
               for (int j = 0; j < synList.getLength(); ++j) {
                   Element synEl = (Element) synList.item(j);

                   listTranslate.add(synEl
                           .getElementsByTagName("text")
                           .item(0)
                           .getTextContent());

               }
           }
       }
           return listTranslate;
   }

    protected static void validateServiceState() throws Exception {
        if(apiKey==null||apiKey.length()<27) {
            throw new RuntimeException("INVALID_API_KEY - Please set the API Key with your Yandex API Key");
        }
    }

}
