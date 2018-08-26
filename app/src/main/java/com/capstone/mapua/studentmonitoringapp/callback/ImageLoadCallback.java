package com.capstone.mapua.studentmonitoringapp.callback;

/**
 * Created by jj on 5/5/2018.
 */

public interface ImageLoadCallback {

    void onSuccess();

    void onError();

    public static class EmptyCallback implements ImageLoadCallback {

        @Override public void onSuccess() {
        }

        @Override public void onError() {
        }
    }
}
