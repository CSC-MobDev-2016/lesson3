package com.csc.lesson3.apis;

import com.csc.lesson3.apis.flickr_image_search.FlickrImageSearchApiService;
import com.csc.lesson3.apis.yandex_translate.YandexTranslateApiService;

/**
 * Created by Pavel on 3/12/2016.
 */
public class ApiServices {
    public final static YandexTranslateApiService YANDEX_TRANSLATE = new YandexTranslateApiService();
    public final static FlickrImageSearchApiService FLICKR_IMAGE_SEARCH = new FlickrImageSearchApiService();

    private ApiServices() {}
}
