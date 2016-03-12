package com.csc.lesson3.apis.common;

/**
 * Created by Pavel on 3/12/2016.
 */
public interface ApiRequestCallback<T> {
    void onSuccess(T result);
    void onError(Throwable error);
}
