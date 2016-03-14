package com.csc.lesson3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 14.03.2016.
 */
public class PixabayResponse {
    public int totalHits;
    public List<Hit> hits = new ArrayList<Hit>();
    public int total;

    public PixabayResponse(int totalHits, List<Hit> hits, int total) {
        this.totalHits = totalHits;
        this.hits = hits;
        this.total = total;
    }
}

class Hit {
    public int previewHeight;
    public int likes;
    public int favorites;
    public String tags;
    public int webformatHeight;
    public int views;
    public int webformatWidth;
    public int previewWidth;
    public int comments;
    public int downloads;
    public String pageURL;
    public String previewURL;
    public String webformatURL;
    public int imageWidth;
    public int userId;
    public String user;
    public String type;
    public int id;
    public String userImageURL;
    public int imageHeight;

    public Hit(int previewHeight, int likes, int favorites, String tags, int webformatHeight, int views, int webformatWidth, int previewWidth, int comments, int downloads, String pageURL, String previewURL, String webformatURL, int imageWidth, int userId, String user, String type, int id, String userImageURL, int imageHeight) {
        this.previewHeight = previewHeight;
        this.likes = likes;
        this.favorites = favorites;
        this.tags = tags;
        this.webformatHeight = webformatHeight;
        this.views = views;
        this.webformatWidth = webformatWidth;
        this.previewWidth = previewWidth;
        this.comments = comments;
        this.downloads = downloads;
        this.pageURL = pageURL;
        this.previewURL = previewURL;
        this.webformatURL = webformatURL;
        this.imageWidth = imageWidth;
        this.userId = userId;
        this.user = user;
        this.type = type;
        this.id = id;
        this.userImageURL = userImageURL;
        this.imageHeight = imageHeight;
    }
}