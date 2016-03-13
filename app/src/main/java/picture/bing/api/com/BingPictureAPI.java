package picture.bing.api.com;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;

public class BingPictureAPI {

    protected final String SERVICE_URL = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Image";
    protected final String ENCODING = "UTF-8";
    protected String apiKey;

    public void setKey(final String key) {
        apiKey = key;
    }

    public PicturesDescription parseResponse(final String response) throws Exception {
        PicturesDescription picturesDescription = new PicturesDescription();
        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject)parser.parse(response);
        JSONObject d = (JSONObject)root.get("d");
        if (d != null) {
            picturesDescription.setNext((String)d.get("__next"));
            JSONArray results = (JSONArray)d.get("results");
            Iterator<JSONObject> iterator = results.iterator();
            while (iterator.hasNext()) {
                PictureDescription picture = new PictureDescription();
                parseResult(iterator.next(), picture);
                picturesDescription.addPictureDescription(picture);
            }
        }
        return picturesDescription;
    }

    public void parseResult(final JSONObject __result, PictureDescription picture) {
        {
            JSONObject __metadata = (JSONObject)__result.get("__metadata");
            if (__metadata != null) {
                Metadata metadata = new Metadata();
                parseMetadata(__metadata, metadata);
                picture.setMetadata(metadata);
            }
        }

        {
            Info info = new Info();
            parseInfo(__result, info);
            picture.setInfo(info);
        }

        {
            JSONObject __thumbnail = (JSONObject)__result.get("Thumbnail");
            if (__thumbnail != null) {
                Thumbnail thumbnail = new Thumbnail();
                parseThumbnail(__thumbnail, thumbnail);
                picture.setThumbnail(thumbnail);
            }
        }
    }
    public void parseMetadata(final JSONObject __metadata, Metadata metadata) {
        metadata.setType((String)__metadata.get("type"));
        metadata.setUri((String)__metadata.get("uri"));
    }

    public void parseInfo(final JSONObject __info, Info info) {
        info.setID((String)__info.get("ID"));
        info.setMediaUrl((String)__info.get("MediaUrl"));
        info.setSourceUrl((String)__info.get("SourceUrl"));
        info.setDisplayUrl((String)__info.get("DisplayUrl"));
        info.setWidth((String)__info.get("Width"));
        info.setHeight((String)__info.get("Height"));
        info.setFileSize((String)__info.get("FileSize"));
        info.setContentType((String)__info.get("ContentType"));
    }

    public void parseThumbnail(final JSONObject __thumbnail, Thumbnail thumbnail) {
        {
            JSONObject __metadata = (JSONObject)__thumbnail.get("__metadata");
            if (__metadata != null) {
                Metadata metadata = new Metadata();
                parseMetadata(__metadata, metadata);
                thumbnail.setMetadata(metadata);
            }
        }

        {
            Info info = new Info();
            parseInfo(__thumbnail, info);
            thumbnail.setInfo(info);
        }
    }

}
