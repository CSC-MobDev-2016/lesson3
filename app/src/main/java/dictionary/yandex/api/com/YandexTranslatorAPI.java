package dictionary.yandex.api.com;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;

public class YandexTranslatorAPI {
    protected final String SERVICE_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup";
    protected final String ENCODING = "UTF-8";
    protected String apiKey;

    public void setKey(final String key) {
        apiKey = key;
    }

    public WordDescription parseResponse(final String response) throws Exception {
        WordDescription wordDescription = new WordDescription();
        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject)parser.parse(response);
        JSONArray def = (JSONArray)root.get("def");
        Iterator<JSONObject> iterator = def.iterator();
        while (iterator.hasNext()) {
            Rank rank = new Rank();
            parseRank(iterator.next(), rank);
            wordDescription.addRank(rank);
        }
        return wordDescription;
    }

    public void parseRank(final JSONObject def, Rank rank) {
        rank.setText((String)def.get("text"));
        rank.setPartOfSpeech((String)def.get("pos"));
        rank.setTranscription((String)def.get("ts"));
        JSONArray tr = (JSONArray)def.get("tr");
        Iterator<JSONObject> iterator = tr.iterator();
        while (iterator.hasNext()) {
            Translation translation = new Translation();
            parseTranslation(iterator.next(), translation);
            rank.addTranslation(translation);
        }
    }

    public void parseTranslation(final JSONObject tr, Translation translation) {
        translation.setText((String)tr.get("text"));
        translation.setGen((String)tr.get("gen"));
        translation.setPartOfSpeech((String)tr.get("pos"));
        {
            JSONArray syn = (JSONArray)tr.get("syn");
            if (syn != null) {
                Iterator<JSONObject> iterator = syn.iterator();
                while (iterator.hasNext()) {
                    Synonym synonym = new Synonym();
                    parseSynonym(iterator.next(), synonym);
                    translation.addSynonym(synonym);
                }
            }
        }
        {
            JSONArray mean = (JSONArray)tr.get("mean");
            if (mean != null) {
                Iterator<JSONObject> iterator = mean.iterator();
                while (iterator.hasNext()) {
                    Meaning meaning = new Meaning();
                    parseMeaning(iterator.next(), meaning);
                    translation.addMeaning(meaning);
                }
            }
        }

        {
            JSONArray ex = (JSONArray)tr.get("ex");
            if (ex != null) {
                Iterator<JSONObject> iterator = ex.iterator();
                while (iterator.hasNext()) {
                    Example example = new Example();
                    parseExample(iterator.next(), example);
                    translation.addExample(example);
                }
            }
        }

    }

    public void parseSynonym(final JSONObject syn, Synonym synonym) {
        synonym.setText((String)syn.get("text"));
        synonym.setGen((String)syn.get("gen"));
        synonym.setPartOfSpeech((String)syn.get("pos"));
    }

    public void parseMeaning(final JSONObject mean, Meaning meaning) {
        meaning.setText((String)mean.get("text"));
    }

    public void parseExample(final JSONObject ex, Example example) {
        example.setText((String)ex.get("text"));
        JSONArray tr = (JSONArray)ex.get("tr");
        if (tr != null) {
            Iterator<JSONObject> iterator = tr.iterator();
            while (iterator.hasNext()) {
                example.addTranslate((String)iterator.next().get("text"));
            }
        }
    }
}
