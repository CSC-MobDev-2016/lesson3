package picture.bing.api.com;

import java.util.ArrayList;

public class PicturesDescription {
    private ArrayList<PictureDescription> pictures = new ArrayList<>();
    private String next;

    public void setNext(final String next) {
        this.next = next;
    }

    public String getNext() {
        return next;
    }

    public void addPictureDescription(final PictureDescription picture) {
        if (picture != null) {
            pictures.add(picture);
        }
    }

    public ArrayList<PictureDescription> getPictures() {
        return pictures;
    }

    public ArrayList<String> getMediaUrlList() {
        ArrayList<String> urlList = new ArrayList<>();
        for (PictureDescription v : pictures) {
            if (v.getInfo() == null || v.getInfo().getMediaUrl() == null) {
                continue;
            }
            urlList.add(v.getInfo().getMediaUrl());
        }
        return urlList;
    }
}

class PictureDescription {
    private Metadata metadata;
    private Info info;
    private Thumbnail thumbnail;

    public void setMetadata(final Metadata metadata) {
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setInfo(final Info info) {
        this.info = info;
    }

    public Info getInfo() {
        return info;
    }

    public void setThumbnail(final Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }
}

class Metadata {
    private String type;
    private String uri;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

class Info {
    private String ID;
    private String MediaUrl;
    private String SourceUrl;
    private String DisplayUrl;
    private Integer Width;
    private Integer Height;
    private Integer FileSize;
    private String ContentType;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMediaUrl() {
        return MediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        MediaUrl = mediaUrl;
    }

    public String getSourceUrl() {
        return SourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        SourceUrl = sourceUrl;
    }

    public String getDisplayUrl() {
        return DisplayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        DisplayUrl = displayUrl;
    }

    public Integer getWidth() {
        return Width;
    }

    public void setWidth(String width) {
        Width = Integer.valueOf(width);
    }

    public void setWidth(Integer width) {
        Width = width;
    }

    public Integer getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = Integer.valueOf(height);
    }

    public void setHeight(Integer height) {
        Height = height;
    }

    public Integer getFileSize() {
        return FileSize;
    }

    public void setFileSize(String fileSize) {
        FileSize = Integer.valueOf(fileSize);
    }

    public void setFileSize(Integer fileSize) {
        FileSize = fileSize;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }
}

class Thumbnail {
    private Metadata metadata;
    private Info info;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
